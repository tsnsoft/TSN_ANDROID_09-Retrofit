package kz.talipovsn.retrofit_demo;

// КЛАСС-МОДЕЛЬ ОДНОЙ ВАЛЮТЫ (соответствует объекту из JSON-массива сайта)
// Пример строки из интернета:
// {"Cur_Abbreviation": "USD", "Cur_Scale": 1, "Cur_Name": "Доллар США", "Cur_OfficialRate": 3.25}
// Важно: имена переменных в классе должны ТОЧНО совпадать с названиями полей в JSON,
// тогда библиотека Gson заполнит их данными автоматически!
public class DataModel {

    // Трехбуквенный код валюты (например, "USD", "EUR", "KZT")
    public String Cur_Abbreviation = "";

    // Масштаб (количество единиц валюты: например, 1 доллар или 100 российских рублей)
    public double Cur_Scale = 1;

    // Полное название валюты на русском языке (например, "Доллар США")
    public String Cur_Name = "";

    // Официальный курс валюты (число с плавающей точкой)
    public double Cur_OfficialRate = 0;

}
