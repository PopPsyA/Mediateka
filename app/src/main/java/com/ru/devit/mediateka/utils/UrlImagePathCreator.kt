package com.ru.devit.mediateka.utils


object UrlImagePathCreator {

    private const val IMG_PATH_W1280 = "https://image.tmdb.org/t/p/w1280/"
    private const val IMG_PATH_W780 = "https://image.tmdb.org/t/p/w780/"
    private const val IMG_PATH_W185 = "https://image.tmdb.org/t/p/w185/"

    fun createPictureUrlFromQuality(qualityType: Quality, imgUrl: String?): String {
        for (quality in Quality.values()) {
            if (quality == qualityType) {
                return qualityType.quality(imgUrl.orEmpty())
            }
        }
        throw IllegalArgumentException("No such $qualityType")
    }

    enum class Quality {
        Quality1280 {
            override fun quality(imgUrl: String): String {
                return create1280pPictureUrl(imgUrl)
            }
        },
        Quality780 {
            override fun quality(imgUrl: String): String {
                return create780pPictureUrl(imgUrl)
            }
        },
        Quality185 {
            override fun quality(imgUrl: String): String {
                return create185pPictureUrl(imgUrl)
            }
        };

        abstract fun quality(imgUrl: String): String
    }

    private fun create1280pPictureUrl(imgUrl: String): String {
        return IMG_PATH_W1280 + imgUrl
    }

    private fun create780pPictureUrl(imgUrl: String): String {
        return IMG_PATH_W780 + imgUrl
    }

    private fun create185pPictureUrl(imgUrl: String): String {
        return IMG_PATH_W185 + imgUrl
    }
}
