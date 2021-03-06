package id.frogobox.footballapps.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar

import id.frogobox.footballapps.R
import id.frogobox.footballapps.models.dataclass.FavoriteMatch
import id.frogobox.footballapps.presenters.FavoriteListMatchPresenter
import id.frogobox.footballapps.views.activities.DetailMatchActivity
import id.frogobox.footballapps.views.adapters.FavoriteMatchRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_favorite_match.view.*
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.startActivity

class FavoriteMatchFragment : androidx.fragment.app.Fragment() {

    private var favorites: MutableList<FavoriteMatch> = mutableListOf()
    private lateinit var presenter: FavoriteListMatchPresenter
    private lateinit var adapter: FavoriteMatchRecyclerViewAdapter
    private lateinit var recyclerView: androidx.recyclerview.widget.RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: androidx.swiperefreshlayout.widget.SwipeRefreshLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_favorite_match, container, false)
        val mLayoutManager = androidx.recyclerview.widget.LinearLayoutManager(activity)
        val divider = androidx.recyclerview.widget.DividerItemDecoration(context, mLayoutManager.orientation)
        // -----------------------------------------------------------------------------------------
        progressBar = rootView.progressBar_FavMatch
        recyclerView = rootView.recyclerView_FavMatch
        swipeRefresh = rootView.swipeRefresh_FavMatch
        // -----------------------------------------------------------------------------------------
        adapter = FavoriteMatchRecyclerViewAdapter(context, favorites){
            startActivity<DetailMatchActivity>(DetailMatchActivity.STRING_EXTRA_FAVORITE to it)
        }
        // -----------------------------------------------------------------------------------------
        presenter = FavoriteListMatchPresenter(context, favorites, progressBar,swipeRefresh, adapter)
        // -----------------------------------------------------------------------------------------
        recyclerView.adapter = adapter
        recyclerView.layoutManager = mLayoutManager
        recyclerView.addItemDecoration(divider)
        // -----------------------------------------------------------------------------------------
        swipeRefresh.onRefresh {
            presenter.showFavorite()
        }
        return rootView
    }

    override fun onResume() {
        super.onResume()
        presenter.showFavorite()
    }

}