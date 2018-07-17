package com.rhino.bjl.control;

import com.rhino.bjl.server.IDataMessage;
import com.rhino.bjl.utils.JsonUtil;
import com.rhino.bjl.utils.ParamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "data/analysis")
public class DataControl extends BaseControl {

    @Autowired
    private IDataMessage dataMessage;

    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("data/index");
        return mav;
    }

    /**
     * 查询连庄或者连闲的第三局
     * @param request
     * @return
     * select * from reet_tbl a INNER JOIN (SELECT * FROM reet_tbl c where c.JISHUCOUNT=1 and c.OUSHUCOUNT = 3) b on a.POINT = b.POINT + 1 and a.ROOMID = b.ROOMID where a.ROOMID='9fae630b-1220-4c5a-b662-820352d7c004' order by a.POINT asc
     */
    @RequestMapping(value = "searchData", method = RequestMethod.POST)
    public ResponseEntity<String> reetAllData(HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        MediaType mediaType = new MediaType("text", "html", Charset.forName("UTF-8"));
        headers.setContentType(mediaType);
        Map<String, Object> param = new HashMap<String, Object>();
        String oneType = ParamUtils.getParameter(request, "oneType", "全部");
        String twoType = ParamUtils.getParameter(request, "twoType", "全部");
        String threeType = ParamUtils.getParameter(request, "threeType", "全部");
        String one = ParamUtils.getParameter(request, "one", "");
        String two = ParamUtils.getParameter(request, "two", "");
        String three = ParamUtils.getParameter(request, "three", "");
        String allCount = ParamUtils.getParameter(request, "allCount", ""); //查询大局数量
        String pages = ParamUtils.getParameter(request, "pages", "1"); //查询页数
        List<HashMap<String, Object>> searchData = dataMessage.findReetList2(oneType,twoType,threeType,one,two,three,allCount,pages);
        int count = searchData.size();
        param.put("searchData", searchData);
        param.put("count", count);
        String json = JsonUtil.toJsonString(param);
        ResponseEntity<String> responseEntity = new ResponseEntity<String>(
                json, headers, HttpStatus.OK);
        return responseEntity;
    }
}
