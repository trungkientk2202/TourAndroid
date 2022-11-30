package com.da.tourandroid.utils;

public class AccountOption {
    private String optionName;
    private int optionLogo;

    public AccountOption(String optionName, int optionLogo) {
        this.optionName = optionName;
        this.optionLogo = optionLogo;
    }

    public String getOptionName() {
        return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }

    public int getOptionLogo() {
        return optionLogo;
    }

    public void setOptionLogo(int optionLogo) {
        this.optionLogo = optionLogo;
    }
}
