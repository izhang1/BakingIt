package nanodegree.izhang.bakingit;

import android.content.Intent;
import android.widget.RemoteViewsService;

import nanodegree.izhang.bakingit.Widget.IngredientProvider;

/**
 * Created by ivanzhang on 12/28/17.
 */

public class RecipeService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

        return (new IngredientProvider(this.getApplicationContext(), intent));
    }

}
