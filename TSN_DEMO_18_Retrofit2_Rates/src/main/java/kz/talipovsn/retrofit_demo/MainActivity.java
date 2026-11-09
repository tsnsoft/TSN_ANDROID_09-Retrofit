package kz.talipovsn.retrofit_demo;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

// ГЛАВНЫЙ ЭКРАН ПРИЛОЖЕНИЯ
public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;                 // Компонент для прокручиваемого списка на экране
    private List<DataModel> dataset = new ArrayList<>(); // Список в памяти, куда сложим загруженные валюты

    // 1. ИНТЕРФЕЙС RETROFIT: указываем путь к JSON-данным на сервере
    interface ApiInterface {
        // Аннотация @GET задает адрес страницы с ежедневными курсами валют
        @GET("/exrates/rates?periodicity=0")
        Call<List<DataModel>> getData(); // Ожидаем получить список объектов DataModel
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Подключаем разметку экрана activity_main.xml

        // 2. НАСТРОЙКА СПИСКА RecyclerView НА ЭКРАНЕ
        recyclerView = findViewById(R.id.posts_recycle_view); // Находим компонент списка на экране по его ID
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // Располагаем элементы строго сверху вниз
        recyclerView.setAdapter(new DataAdapter(dataset));            // Подключаем наш класс-адаптер к списку

        // 3. СОЗДАНИЕ И НАСТРОЙКА КЛИЕНТА RETROFIT
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.nbrb.by")                      // Базовый адрес сайта с открытым API
                .addConverterFactory(GsonConverterFactory.create()) // Автоматический переводчик из JSON в класс DataModel
                .build();

        // Создаем рабочий объект для отправки запроса
        ApiInterface api = retrofit.create(ApiInterface.class);

        // 4. АСИНХРОННЫЙ ЗАПРОС В ИНТЕРНЕТ (метод enqueue выполняется в фоновом потоке!)
        api.getData().enqueue(new Callback<List<DataModel>>() {

            // Этот метод сработает автоматически, когда сервер пришлет ответ
            @Override
            public void onResponse(Call<List<DataModel>> call, Response<List<DataModel>> response) {
                // Достаем готовый список валют из ответа сервера
                List<DataModel> list = response.body();

                // Проверяем, что список не пустой
                if (list != null) {
                    // ШАГ А: Находим в списке курс казахстанского тенге (KZT), чтобы перевести всё в тенге
                    double kztRate = 1;
                    for (DataModel item : list) {
                        if ("KZT".equals(item.Cur_Abbreviation)) {
                            kztRate = item.Cur_OfficialRate / item.Cur_Scale; // Вычисляем стоимость 1 тенге
                            break;
                        }
                    }

                    // ШАГ Б: Пересчитываем все остальные валюты мира в казахстанские тенге (KZT)
                    for (DataModel item : list) {
                        if (!"KZT".equals(item.Cur_Abbreviation)) {
                            item.Cur_OfficialRate = item.Cur_OfficialRate / kztRate; // Перевод курса в KZT
                            dataset.add(item); // Добавляем готовую валюту в наш список для экрана
                        }
                    }
                } else {
                    // Если сервер вернул пустоту — вызываем метод показа ошибки
                    showError();
                }

                // Даем команду списку на экране обновиться и показать загруженные данные
                recyclerView.getAdapter().notifyDataSetChanged();
            }

            // Этот метод сработает, если на телефоне нет интернета или сайт недоступен
            @Override
            public void onFailure(Call<List<DataModel>> call, Throwable t) {
                showError(); // Добавляем строку с сообщением об ошибке
                recyclerView.getAdapter().notifyDataSetChanged(); // Обновляем экран
            }
        });
    }

    // Вспомогательный метод для вывода сообщения об ошибке в список на экране
    private void showError() {
        DataModel errorItem = new DataModel();
        errorItem.Cur_Name = "Нет данных";
        errorItem.Cur_OfficialRate = 0;
        dataset.add(errorItem);
    }
}
