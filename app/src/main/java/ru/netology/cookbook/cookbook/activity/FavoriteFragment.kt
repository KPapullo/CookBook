package ru.netology.cookbook.cookbook.activity

import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import ru.netology.cookbook.R
import ru.netology.cookbook.cookbook.adapter.RecyclerAdapter
import ru.netology.cookbook.cookbook.viewModel.RecipeViewModel
import ru.netology.cookbook.databinding.FavoriteFragmentBinding


class FavoriteFragment : Fragment(R.layout.favorite_fragment) {

    private val viewModel by activityViewModels<RecipeViewModel>()
    private lateinit var binding: FavoriteFragmentBinding
    private lateinit var adapter: RecyclerAdapter
    private lateinit var recipeRecyclerView: RecyclerView
    private lateinit var emptyView: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FavoriteFragmentBinding.inflate(
        layoutInflater, container, false
    ).also {
        binding = it
        setHasOptionsMenu(true)

        adapter = RecyclerAdapter(viewModel)
        recipeRecyclerView = binding.recipeRecyclerView
        emptyView = binding.emptyView
        recipeRecyclerView.adapter = adapter

        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(recipeRecyclerView)

        setFragmentResultListener(
            requestKey = RecipeCreateFragment.REQUEST_KEY
        ) { requestKey, bundle ->
            if (requestKey != RecipeCreateFragment.REQUEST_KEY) return@setFragmentResultListener
            val titleRecipe = bundle.getString(
                RecipeCreateFragment.TITLE_KEY
            ) ?: return@setFragmentResultListener
            viewModel.onSaveRecipe(titleRecipe)
        }

        viewModel.navigateToRecipeEvent.observe(viewLifecycleOwner) { recipe ->
            findNavController().navigate(R.id.action_favoriteFragment_to_recipeViewFragment)
        }

        viewModel.navigateToRecipeEditorScreenEvent.observe(viewLifecycleOwner) { recipe ->
            findNavController().navigate(R.id.action_favoriteFragment_to_nav_graph_create_recipe)
        }

        viewModel.filtratedData.observe(viewLifecycleOwner) { recipes ->
            val listFavorite = recipes.filter { recipe ->
                recipe.liked
            }
            emptyData(listFavorite.size)
            adapter.submitList(listFavorite)
        }

        binding.buttonAddRecipe.setOnClickListener {
            viewModel.onInsertClicked()
            findNavController().navigate(R.id.action_favoriteFragment_to_nav_graph_create_recipe)
        }

    }.root

    private fun emptyData(sizeList: Int) {
        if (sizeList == 0) {
            recipeRecyclerView.visibility = View.INVISIBLE
            emptyView.visibility = View.VISIBLE
        } else {
            recipeRecyclerView.visibility = View.VISIBLE
            emptyView.visibility = View.INVISIBLE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.action_bar_menu, menu)
        val searchItem = menu.findItem(R.id.search)
        val searchView: SearchView = searchItem.actionView as SearchView
        searchView.imeOptions = EditorInfo.IME_ACTION_DONE
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.searchQuery.value = newText
                return true
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }
}

private var simpleCallback =
    object : ItemTouchHelper.SimpleCallback(
        ItemTouchHelper.UP.or(ItemTouchHelper.DOWN), 0
    ) {

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

        }
    }