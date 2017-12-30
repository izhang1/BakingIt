package nanodegree.izhang.bakingit.Widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;

import nanodegree.izhang.bakingit.R;
import nanodegree.izhang.bakingit.RecipeService;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidget extends AppWidgetProvider {


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        Log.v("UpdateAppWidget", " Called");
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);

        //RemoteViews Service needed to provide adapter for ListView
        Intent recipeServiceIntent = new Intent(context, RecipeService.class);
        //passing app widget id to that RemoteViews Service
        recipeServiceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        //setting a unique Uri to the intent
        //don't know its purpose to me right now
        recipeServiceIntent.setData(Uri.parse(
                recipeServiceIntent.toUri(Intent.URI_INTENT_SCHEME)));
        //setting adapter to listview of the widget
        views.setRemoteAdapter(appWidgetId, R.id.list_ingredients, recipeServiceIntent);
        //setting an empty view in case of no data
        views.setEmptyView(R.id.list_ingredients, R.id.tv_norecipe);


        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        Log.v("onUpdate", " Called");

        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {

            RecipeWidget.updateAppWidget(context, appWidgetManager, appWidgetId);
        }

        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

