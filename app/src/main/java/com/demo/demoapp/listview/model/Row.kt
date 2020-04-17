package com.demo.demoapp.listview.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class Row {
    @SerializedName("title")
    @Expose
    private var title: String? = null
    @SerializedName("description")
    @Expose
    private var description: String? = null
    @SerializedName("imageHref")
    @Expose
    private var imageHref: Any? = null

    fun getTitle(): String? {
        return title
    }

    fun setTitle(title: String?) {
        this.title = title
    }

    fun getDescription(): String? {
        return description
    }

    fun setDescription(description: String?) {
        this.description = description
    }

    fun getImageHref(): Any? {
        return imageHref
    }

    fun setImageHref(imageHref: Any?) {
        this.imageHref = imageHref
    }
}