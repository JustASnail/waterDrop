package com.netease.nim.uikit.emoji;


import android.content.res.AssetManager;
import android.text.TextUtils;

import com.netease.nim.uikit.NimUIKit;
import com.netease.nim.uikit.common.util.file.FileUtil;
import com.nostra13.universalimageloader.core.download.ImageDownloader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StickerCategory {
    private static final long serialVersionUID = -81692490861539040L;

    private String name;//贴图包名
    private String title;//显示的标题
    private boolean system;//是否是系统内置表情
    private int order = 0;//默认顺序

    private transient List<StickerItem> stickers;

    public StickerCategory(String name, String title, boolean system, int order) {
        this.name = name;
        this.title = title;
        this.system = system;
        this.order = order;
        loadStickerData();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isSystem() {
        return system;
    }

    public void setSystem(boolean system) {
        this.system = system;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public List<StickerItem> getStickers() {
        return stickers;
    }

    public void setStickers(List<StickerItem> stickers) {
        this.stickers = stickers;
    }

    public boolean hasStickers() {
        return stickers != null && stickers.size() > 0;
    }

    public int getCount() {
        if (stickers == null || stickers.isEmpty()) {
            return 0;
        }

        return stickers.size();
    }

    public String getCoverImgPath() {
        AssetManager assetManager = NimUIKit.getContext().getResources().getAssets();
        try {
            String[] files = assetManager.list("sticker");
            for (String name : files) {
                if (!FileUtil.hasExtentsion(name)) {
                    if (TextUtils.equals(this.name, name)) {
                        String coverName = name;
                        if (!name.contains(".png")) {
                            coverName += ".png";
                        }

                        String path = "sticker/" + coverName;

                        return ImageDownloader.Scheme.ASSETS.wrap(path);
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
      /*  for (File file : new File(LQREmotionKit.getStickerPath()).listFiles()) {
            if (file.isFile() && file.getName().startsWith(name)) {
                return "file://" + file.getAbsolutePath();
            }
        }*/
        return null;
    }

    public List<StickerItem> loadStickerData() {
        List<StickerItem> stickers = new ArrayList<>();
        AssetManager assetManager = NimUIKit.getContext().getResources().getAssets();
        try {
            String[] files = assetManager.list("sticker/" + name);
            for (String file : files) {
                stickers.add(new com.netease.nim.uikit.emoji.StickerItem(name, file));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //补充最后一页缺少的贴图
        int tmp = stickers.size() % EmotionLayout.STICKER_PER_PAGE;
        if (tmp != 0) {
            int tmp2 = EmotionLayout.STICKER_PER_PAGE - (stickers.size() - (stickers.size() / EmotionLayout.STICKER_PER_PAGE) * EmotionLayout.STICKER_PER_PAGE);
            for (int i = 0; i < tmp2; i++) {
                stickers.add(new StickerItem("", ""));
            }
        }


        this.setStickers(stickers);
        return stickers;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof StickerCategory)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        StickerCategory r = (StickerCategory) obj;
        return r.getName().equals(getName());
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }}
