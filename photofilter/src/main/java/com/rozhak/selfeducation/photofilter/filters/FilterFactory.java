package com.rozhak.selfeducation.photofilter.filters;

import com.rozhak.selfeducation.photofilter.filters.impl.Sepia;

public class FilterFactory {
	
	public Filter getFilter(Filters filtername) throws ClassNotFoundException {
		Filter filter = null;
		if(filtername==Filters.SEPIA){
			FilterGetter<Sepia> sepia = Sepia::new;
			filter = sepia.getFilter();
		}
		else {
			return null;
		}
		return filter;
	}

}
