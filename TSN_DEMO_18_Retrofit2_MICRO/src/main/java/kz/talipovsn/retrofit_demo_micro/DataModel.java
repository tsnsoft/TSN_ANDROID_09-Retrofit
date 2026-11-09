package kz.talipovsn.retrofit_demo_micro;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

// КЛАСС-МОДЕЛЬ ДАННЫХ С САЙТА https://blockchain.info/ticker
// Формат JSON: {"USD": {"15m": 85000.0, "last": 85000.0, "buy": 85000.0, "sell": 85000.0, "symbol": "$"}, ...}
// Для генерации классов из JSON можно использовать https://www.jsonschema2pojo.org/

public class DataModel {

    @SerializedName("USD")
    @Expose
    private Currency uSD;

    @SerializedName("EUR")
    @Expose
    private Currency eUR;

    @SerializedName("GBP")
    @Expose
    private Currency gBP;

    public Currency getUSD() {
        return uSD;
    }

    public void setUSD(Currency uSD) {
        this.uSD = uSD;
    }

    public Currency getEUR() {
        return eUR;
    }

    public void setEUR(Currency eUR) {
        this.eUR = eUR;
    }

    public Currency getGBP() {
        return gBP;
    }

    public void setGBP(Currency gBP) {
        this.gBP = gBP;
    }
}

class Currency {

    @SerializedName("last")
    @Expose
    private Double last; // Последний актуальный курс

    @SerializedName("buy")
    @Expose
    private Double buy;  // Курс покупки

    @SerializedName("sell")
    @Expose
    private Double sell; // Курс продажи

    @SerializedName("symbol")
    @Expose
    private String symbol; // Символ валюты ($, €, £)

    public Double getLast() {
        return last;
    }

    public void setLast(Double last) {
        this.last = last;
    }

    public Double getBuy() {
        return buy;
    }

    public void setBuy(Double buy) {
        this.buy = buy;
    }

    public Double getSell() {
        return sell;
    }

    public void setSell(Double sell) {
        this.sell = sell;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}