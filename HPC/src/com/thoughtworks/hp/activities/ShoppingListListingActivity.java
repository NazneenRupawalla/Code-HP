package com.thoughtworks.hp.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.thoughtworks.hp.R;
import com.thoughtworks.hp.adapters.ShoppingListListingAdapter;
import com.thoughtworks.hp.datastore.ShoppingListTable;
import com.thoughtworks.hp.models.ShoppingList;
import com.thoughtworks.hp.presenters.ShoppingListListingPresenter;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListListingActivity extends Activity {

    private ShoppingListTable shoppingListTable;
    private ListView shoppingListsListingView;
    private ShoppingListListingAdapter shoppingListListingAdapter;
    private List<ShoppingList> shoppingLists = new ArrayList<ShoppingList>();
    private ShoppingListListingPresenter shoppingListListingPresenter;

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.shopping_list_listing);

        shoppingListListingPresenter = new ShoppingListListingPresenter();

        initDependencies();
        updateTotalListCountOnView();
        bindEventsToAddNewList();
        bindBackButton();
        initListingView();
    }

    private void bindBackButton() {
        View backToHomeButton = this.findViewById(R.id.back_to_home_button);
        backToHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShoppingListListingActivity.this.finish();
            }
        });
    }

    private void updateTotalListCountOnView() {
        TextView totalListCountView = (TextView) this.findViewById(R.id.shopping_lists_count_text);
        totalListCountView.setText(shoppingListListingPresenter.totalCountLable());

    }

    private void initDependencies() {
        this.shoppingListTable = new ShoppingListTable();
    }

    private void initListingView() {
        updateAllShoppingLists();
        this.shoppingListListingAdapter = new ShoppingListListingAdapter(this, R.layout.shopping_list_line_item, shoppingLists);
        this.shoppingListsListingView = (ListView) this.findViewById(R.id.shopping_list_lists);
        this.shoppingListsListingView.setAdapter(this.shoppingListListingAdapter);
        this.shoppingListsListingView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                long shoppingListId = shoppingLists.get(position).getId();
                startActivityToAddProductsToShoppingListWith(shoppingListId);
            }
        });
    }

    private void updateAllShoppingLists() {
        shoppingLists.clear();
        shoppingLists.addAll(shoppingListTable.findAll());
    }

    private void updateListingView() {
        updateAllShoppingLists();
        shoppingListListingAdapter.notifyDataSetChanged();
    }

    private void bindEventsToAddNewList() {
        ImageView addNewShoppingListButton = (ImageView) this.findViewById(R.id.add_image_button);
        addNewShoppingListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInputDialogForNewShoppingList();
            }
        });
    }

    private void addNewShoppingListAndRefreshView(String shoppingListName) {
        //All of this needs to move to a separate thread instead of main. Will do that soon.
        ShoppingList newShoppingList = new ShoppingList(-1, shoppingListName);
        shoppingListTable.create(newShoppingList);

        updateListingView();
        updateTotalListCountOnView();
        startActivityToAddProductsToShoppingListWith(newShoppingList.getId());
    }

    private void startActivityToAddProductsToShoppingListWith(long id) {
        Intent addItemsToShoppingList = new Intent(ShoppingListListingActivity.this, AddProductActivity.class);
        addItemsToShoppingList.putExtra(ShoppingList.SHOPPING_LIST_ID, id);
        startActivity(addItemsToShoppingList);
    }

    private void showInputDialogForNewShoppingList() {
        AlertDialog.Builder newShoppingListBox = new AlertDialog.Builder(this);

        newShoppingListBox.setTitle("Create New Shopping List");
        newShoppingListBox.setMessage("Enter a name");

        final EditText inputText = new EditText(this);
        newShoppingListBox.setView(inputText);

        newShoppingListBox.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String shoppingListName = inputText.getText().toString();
                addNewShoppingListAndRefreshView(shoppingListName);
            }
        });

        newShoppingListBox.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //do nothing
            }
        });

        newShoppingListBox.show();
    }
}
