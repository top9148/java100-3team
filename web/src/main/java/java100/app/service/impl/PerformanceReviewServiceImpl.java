package java100.app.service.impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java100.app.dao.performance.PerformanceReviewDao;
import java100.app.dao.performance.ReviewFileDao;
import java100.app.domain.performance.PerformanceReview;
import java100.app.domain.performance.ReviewFile;
import java100.app.service.PerformanceReviewService;

@Service
public class PerformanceReviewServiceImpl implements PerformanceReviewService {

    @Autowired PerformanceReviewDao performanceReviewDao;
    @Autowired ReviewFileDao reviewFileDao;

    
    @Override
    public List<PerformanceReview> list(int pageNo, int pageSize, Map<String, Object> options) {
    	HashMap<String,Object> params = new HashMap<>();
    	params.put("startIndex", (pageNo - 1) * pageSize);
    	params.put("size", pageSize);
    	
    	if (options != null) {
    		params.putAll(options);
    	}
    	return performanceReviewDao.findAll(params);
    }
    
    @Override
    public PerformanceReview get(int no) {
    	performanceReviewDao.updateViewCount(no);
    	PerformanceReview performanceReview = performanceReviewDao.findByNo2(no);
    	
    	return performanceReview;
    }

    @Override
    public List<ReviewFile> getMedias(int reviewFileNo) {
    	return reviewFileDao.findByPerformanceReviewNo(reviewFileNo);
    }

    @Override
    public int getTotalCount() {
    	return performanceReviewDao.countAll();
    }

    @Override
	public int add(PerformanceReview performanceReview) {
        int count = performanceReviewDao.insert(performanceReview);
        System.out.println(performanceReview.getReviewNo());
        
        this.addReviewFiles(performanceReview.getReviewFiles(), performanceReview.getReviewNo());
        System.out.println(count + " <= PerformanceServiceImpl");
        System.out.println(performanceReview.toString() + " <= PerformanceServiceImpl");
        
        return count;
	}


	@Override
	public int update(PerformanceReview performanceReview) {
        return performanceReviewDao.update(performanceReview);
	}

	@Override
	public int delete(int no) {
        return performanceReviewDao.delete(no);
	}



	@Override
	public void addReviewFiles(List<ReviewFile> reviewFiles, int reviewNo) {
		  for (ReviewFile reviewMedia : reviewFiles) {
	            System.out.println(reviewMedia.toString());
	            reviewMedia.setReviewNo(reviewNo);
	            reviewFileDao.insert(reviewMedia);
	        }
		
	}









    

}












