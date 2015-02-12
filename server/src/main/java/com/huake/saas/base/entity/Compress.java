package com.huake.saas.base.entity;

public class Compress {
	/**
     * 构造函数
     * 
     * @param width
     * @param height
     * @param quartility
     */
    public Compress(int width, int height, int quartility, String name) {
            this.width = width;
            this.height = height;
            this.quartility = quartility;
            this.name = name;
    }

    /**
     * 宽
     */
    private int width;
    /**
     * 高
     */
    private int height;
    /**
     * 质量
     */
    private int quartility;
    
    /**
     * 标识名
     */
    private String name;

    public int getWidth() {
            return width;
    }

    public void setWidth(int width) {
            this.width = width;
    }

    public int getHeight() {
            return height;
    }

    public void setHeight(int height) {
            this.height = height;
    }

    public int getQuartility() {
            return quartility;
    }

    public void setQuartility(int quartility) {
            this.quartility = quartility;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
