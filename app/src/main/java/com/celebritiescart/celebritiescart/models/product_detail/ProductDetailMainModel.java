package com.celebritiescart.celebritiescart.models.product_detail;

import com.google.gson.JsonArray;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailMainModel
{
    public String visibility;

    public String type_id;

    public String created_at;

    public Extension_attributes extension_attributes;

    public List<JSONObject> tierPrices = null;

    public JsonArray custom_attributes;

    public String attribute_set_id;

    public String updated_at;

    public String price;

    public ArrayList<Media_gallery_entries> media_gallery_entries;

    public String name;

    public List<JSONObject> options = null;

    public String id;

    public String sku;

    public List<JSONObject> product_links = null;

    public String status;

    
}