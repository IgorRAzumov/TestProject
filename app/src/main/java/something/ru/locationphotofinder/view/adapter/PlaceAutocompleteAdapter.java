package something.ru.locationphotofinder.view.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.text.style.CharacterStyle;
import android.text.style.StyleSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.TextView;

import com.google.android.gms.location.places.AutocompletePrediction;

import java.util.ArrayList;

import something.ru.locationphotofinder.presenter.geo_data.GeoDataPresenter;

public class PlaceAutocompleteAdapter extends ArrayAdapter<AutocompletePrediction>
        implements Filterable {
    private static final String TAG = PlaceAutocompleteAdapter.class.getSimpleName();
    private static final CharacterStyle STYLE_BOLD = new StyleSpan(Typeface.BOLD);

    private final AutocompleteFilter autocompleteFilter;
    private final GeoDataPresenter presenter;

    public PlaceAutocompleteAdapter(Context context, GeoDataPresenter presenter) {
        super(context, android.R.layout.simple_expandable_list_item_2, android.R.id.text1);
        this.presenter = presenter;
        autocompleteFilter = new AutocompleteFilter();
    }

    @Override
    public int getCount() {
        //  return resultsList.size();//
        return presenter.getCount();
    }

    @Override
    public AutocompletePrediction getItem(int position) {
        // return resultsList.get(position);//
        return presenter.getItem(position);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View row = super.getView(position, convertView, parent);

        AutocompletePrediction item = getItem(position);
        if (item != null) {
            TextView textView1 = row.findViewById(android.R.id.text1);
            TextView textView2 = row.findViewById(android.R.id.text2);
            textView1.setText(item.getPrimaryText(STYLE_BOLD));
            textView2.setText(item.getSecondaryText(STYLE_BOLD));
        }
        return row;
    }

    @NonNull
    @Override
    public AutocompleteFilter getFilter() {
        return autocompleteFilter;
    }

    class AutocompleteFilter extends android.widget.Filter {
        @Override
        protected FilterResults performFiltering(@NonNull CharSequence constraint) {
            ArrayList<AutocompletePrediction> filterData = presenter
                    .loadAutocompletePredictions(constraint.toString());
            FilterResults results = new FilterResults();
            results.values = filterData;
            results.count = (filterData != null) ? filterData.size() : 0;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, android.widget.Filter.FilterResults
                results) {

            if (results.count != 0) {
                // resultsList = (ArrayList<AutocompletePrediction>)  results.values;
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            if (resultValue instanceof AutocompletePrediction) {
                return ((AutocompletePrediction) resultValue).getFullText(null);
            } else {
                return super.convertResultToString(resultValue);
            }
        }
    }
}
