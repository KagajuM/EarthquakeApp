package com.example.mkagaju.earthquakeapp_20;

import android.content.res.Resources;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;


/**
 * Fork of the RecyclerViewMatcher from https://github.com/dannyroa/espresso-samples
 */
public class RecyclerViewInternalMatcher
{
    private final int recyclerViewId;
    private final int parentViewId;
    private final int tabPosition;

    public RecyclerViewInternalMatcher(int recyclerViewId)
    {
        this.recyclerViewId = recyclerViewId;
        this.parentViewId = -1;
        this.tabPosition = -1;
    }

    public RecyclerViewInternalMatcher(int recyclerViewId, int parentViewId, int tabPosition)
    {
        this.recyclerViewId = recyclerViewId;
        this.parentViewId = parentViewId;
        this.tabPosition = tabPosition;
    }

    /**
     * Returns a matcher for the view at the provided position within the recycler view.
     * @param position 0-based
     * @param targetViewId the id of the view within that row's hierarchy
     * @return not null
     */
    public Matcher<View> atPositionOnView(final int position, final int targetViewId)
    {
        return new TypeSafeMatcher<View>()
        {
            Resources resources = null;
            View childView;

            public void describeTo(Description description)
            {
                String idDescription = Integer.toString(recyclerViewId);
                if (this.resources != null)
                {
                    try
                    {
                        idDescription = this.resources.getResourceName(recyclerViewId);
                    }
                    catch (Resources.NotFoundException e)
                    {
                        idDescription = String.format("%s (resource name not found)", recyclerViewId);
                    }
                }

                description.appendText("with id: " + idDescription);
            }

            public boolean matchesSafely(View view)
            {
                this.resources = view.getResources();

                if (childView == null)
                {
                    RecyclerView recyclerView = null;
                    if (tabPosition != -1 || parentViewId != -1)
                    {
                        ViewPager pager = (ViewPager)view.getRootView().findViewById(parentViewId);
                        if (pager != null && tabPosition < pager.getChildCount())
                            recyclerView =
                                    (RecyclerView)pager.getChildAt(tabPosition).findViewById(recyclerViewId);
                    }
                    else
                        recyclerView = (RecyclerView)view.getRootView().findViewById(recyclerViewId);

                    if (recyclerView != null && recyclerView.getId() == recyclerViewId)
                    {
                        RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(position);
                        if (viewHolder != null)
                            childView = viewHolder.itemView;
                        else
                            return false;
                    }
                    else
                        return false;
                }

                return view == (targetViewId == -1 ? childView : childView.findViewById(targetViewId));
            }
        };
    }
}
