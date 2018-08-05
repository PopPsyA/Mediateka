package com.ru.devit.mediateka.utils;


public final class UrlImagePathCreator {
    private static final String IMG_PATH_W1280 = "https://image.tmdb.org/t/p/w1280/";
    private static final String IMG_PATH_W780 = "https://image.tmdb.org/t/p/w780/";
    private static final String IMG_PATH_W185 = "https://image.tmdb.org/t/p/w185/";

    public static String create1280pPictureUrl(String imgUrl){
        return IMG_PATH_W1280 + imgUrl;
    }

    public static String create780pPictureUrl(String imgUrl){
        return IMG_PATH_W780 + imgUrl;
    }

    public static String create185pPictureUrl(String imgUrl){
        return IMG_PATH_W185 + imgUrl;
    }

    public static String createPictureUrlFromQuality(String imgUrl , String quality){
        return Quality.valueOf("Quality" + quality).quality(imgUrl);
    }

    enum Quality{
        Quality1280 {
            @Override
            public String quality(String imgUrl) {
                return create1280pPictureUrl(imgUrl);
            }
        } ,
        Quality780 {
            @Override
            public String quality(String imgUrl) {
                return create780pPictureUrl(imgUrl);
            }
        } ,
        Quality185 {
            @Override
            public String quality(String imgUrl) {
                return create185pPictureUrl(imgUrl);
            }
        };

        public abstract String quality(String imgUrl);
    }
}
