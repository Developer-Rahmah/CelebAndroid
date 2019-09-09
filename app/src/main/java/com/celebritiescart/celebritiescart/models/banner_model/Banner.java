package com.celebritiescart.celebritiescart.models.banner_model;

public class Banner
{
    private String store_id;


    private String banner_position;

    private String banner_type;

    private String banner_text;

    private String banner;

    private String id;

    private String banner_link_text;

    private String link_id;

    public String getChildren_count() {
        return children_count;
    }

    public void setChildren_count(String children_count) {
        this.children_count = children_count;
    }

    private String children_count;

    public String getStore_id ()
    {
        return store_id;
    }

    public void setStore_id (String store_id)
    {
        this.store_id = store_id;
    }

    public String getBanner_type ()
    {
        return banner_type;
    }

    public String getBanner_position() {
        return banner_position;
    }

    public void setBanner_position(String banner_position) {
        this.banner_position = banner_position;
    }
    public void setBanner_type (String banner_type)
    {
        this.banner_type = banner_type;
    }

    public String getBanner_text ()
    {
        return banner_text;
    }

    public void setBanner_text (String banner_text)
    {
        this.banner_text = banner_text;
    }

    public String getBanner ()
    {
        return banner;
    }

    public void setBanner (String banner)
    {
        this.banner = banner;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getBanner_link_text ()
    {
        return banner_link_text;
    }

    public void setBanner_link_text (String banner_link_text)
    {
        this.banner_link_text = banner_link_text;
    }

    public String getLink_id ()
    {
        return link_id;
    }

    public void setLink_id (String link_id)
    {
        this.link_id = link_id;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [store_id = "+store_id+", banner_type = "+banner_type+", banner_text = "+banner_text+", banner = "+banner+", id = "+id+", banner_link_text = "+banner_link_text+", link_id = "+link_id+",banner_position="+banner_position+"]";
    }
}
			