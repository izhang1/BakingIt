package nanodegree.izhang.bakingit;

import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViewsService;

/**
 * Created by ivanzhang on 12/28/17.
 */

public class RecipeService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

        return (new IngredientProvider(this.getApplicationContext(), intent));
    }

}
