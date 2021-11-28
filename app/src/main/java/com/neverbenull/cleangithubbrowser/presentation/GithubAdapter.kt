package com.neverbenull.cleangithubbrowser.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.neverbenull.cleangithubbrowser.databinding.ItemRepoBinding
import com.neverbenull.cleangithubbrowser.domain.model.RepoModel

class GithubAdapter : PagingDataAdapter<RepoModel, GithubAdapter.RepoViewHolder>(POST_COMPARATOR) {

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onBindViewHolder(
        holder: RepoViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if(payloads.isNotEmpty()) {
            val item = getItem(position)
            holder.update(item)
        } else {
            onBindViewHolder(holder, position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        return RepoViewHolder.create(parent)
    }

    class RepoViewHolder constructor(
        private val binding: ItemRepoBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: RepoModel?) {
            with(binding) {
                this.repo = item
            }
        }

        fun update(item: RepoModel?) {
            with(binding) {
                this.repo = item
            }
        }

        companion object {
            fun create(parent: ViewGroup) : RepoViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemRepoBinding.inflate(inflater, parent, false)

                return RepoViewHolder(binding)
            }
        }
    }

    companion object {
        private val PAYLOAD_SCORE = Any()
        val POST_COMPARATOR = object : DiffUtil.ItemCallback<RepoModel>() {
            override fun areContentsTheSame(oldItem: RepoModel, newItem: RepoModel): Boolean =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: RepoModel, newItem: RepoModel): Boolean =
                oldItem.id == newItem.id

            override fun getChangePayload(oldItem: RepoModel, newItem: RepoModel): Any? {
                return if (sameExceptScore(oldItem, newItem)) {
                    PAYLOAD_SCORE
                } else {
                    null
                }
            }
        }

        private fun sameExceptScore(oldItem: RepoModel, newItem: RepoModel): Boolean {
            // DON'T do this copy in a real app, it is just convenient here for the demo :)
            // because reddit randomizes scores, we want to pass it as a payload to minimize
            // UI updates between refreshes
            return oldItem.copy(id = newItem.id) == newItem
        }
    }
}