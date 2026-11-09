package kz.talipovsn.retrofit_demo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import java.util.Locale;

// КЛАСС-АДАПТЕР ДЛЯ СПИСКА RecyclerView
// Его задача — брать данные из списка (List<DataModel>) и заполнять ими карточки на экране
public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    // Список всех валют, который мы получили из интернета
    private List<DataModel> dataList;

    // Конструктор адаптера: принимает список с данными из MainActivity
    public DataAdapter(List<DataModel> dataList) {
        this.dataList = dataList;
    }

    // Внутренний класс ViewHolder — хранит ссылки на текстовые поля (TextView) одной строки списка
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name; // Поле для названия валюты (например, "1 Доллар США")
        TextView rate; // Поле для курса валюты (например, "495.20 KZT")

        ViewHolder(View itemView) {
            super(itemView);
            // Находим текстовые компоненты внутри макета одной строки (post_item.xml)
            name = itemView.findViewById(R.id.postitem_name);
            rate = itemView.findViewById(R.id.postitem_rate);
        }
    }

    // Метод 1: Создает новый визуальный элемент (карточку строки) из XML-файла post_item.xml
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item,
                parent, false);
        return new ViewHolder(v);
    }

    // Метод 2: Заполняет конкретную строку списка данными по её номеру (position)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Берем одну валюту из списка по её порядковому номеру
        DataModel data = dataList.get(position);

        // Выводим масштаб и название валюты (например: "1 Доллар США" или "100 Российских рублей")
        holder.name.setText((int) data.Cur_Scale + " " + data.Cur_Name);

        // Если курс больше нуля — красиво округляем до 2 знаков после запятой и дописываем "KZT"
        if (data.Cur_OfficialRate > 0) {
            holder.rate.setText(String.format(Locale.US, "%.2f KZT", data.Cur_OfficialRate));
        } else {
            // Если курс равен 0 (значит произошла ошибка сети) — выводим подсказку
            holder.rate.setText("Проверьте интернет");
        }
    }

    // Метод 3: Возвращает общее количество элементов в списке
    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
