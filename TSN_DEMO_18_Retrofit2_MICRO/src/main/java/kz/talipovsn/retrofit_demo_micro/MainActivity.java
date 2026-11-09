package kz.talipovsn.retrofit_demo_micro;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class MainActivity extends AppCompatActivity {

    private TextView textView;

    // Интерфейс запроса к API
    interface ApiInterface {
        @GET("ticker") // Запрос к API для получения данных о курсах валют
        Call<DataModel> getData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        onClick(null); // Загружаем данные при запуске
    }

    // Обработчик кнопки "Обновить"
    public void onClick(View view) {
        textView.setText("Загрузка...");

        // Создаем экземпляр Retrofit для выполнения HTTP-запросов
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://blockchain.info/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Создаем экземпляр интерфейса API
        ApiInterface api = retrofit.create(ApiInterface.class);

        // Асинхронный запрос к API для получения данных о курсах валют
        api.getData().enqueue(new Callback<DataModel>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<DataModel> call, Response<DataModel> response) {
                DataModel data = response.body();
                if (data != null && data.getUSD() != null) {
                    textView.setText("1 BTC = " + data.getUSD().getLast() + " $\n" +
                            "1 BTC = " + data.getEUR().getLast() + " €");
                } else {
                    textView.setText(R.string.error);
                }
            }

            // Обработка ошибки при запросе к API
            @Override
            public void onFailure(Call<DataModel> call, Throwable t) {
                textView.setText(R.string.error);
            }
        });
    }
}