package com.aravind.pepultask.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.PagerAdapter.POSITION_NONE
import com.aravind.pepultask.data.model.Post
import com.aravind.pepultask.ui.post.PostFragment

class PagerAdapter(fm: FragmentManager, posts: ArrayList<Post>) : FragmentPagerAdapter(fm) {

    private lateinit var posts: ArrayList<Post>

    init {
        this.posts = posts;
    }

    override fun getCount(): Int {
          return posts.size
    }

    override fun getItemPosition(`object`: Any): Int {
        return -2
    }


    fun removeData(pos:Int) {
        this.posts.removeAt(pos)
        notifyDataSetChanged()
    }
    fun appendData(dataList: List<Post>) {
        this.posts.addAll(dataList)
        notifyDataSetChanged()
    }

    fun updateData(dataList: List<Post>) {
       // this.posts.clear()
        this.posts.addAll(dataList)
        notifyDataSetChanged()
    }


    override fun getItem(position: Int): Fragment {
        if (posts.size != 0) {
            val id = posts[position].id
            val file = posts[position].file
            val type = posts[position].file_type
            return PostFragment.newInstance(file, type, id,position)
        } else return PostFragment()
    }
}
