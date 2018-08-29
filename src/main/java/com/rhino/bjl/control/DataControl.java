package com.rhino.bjl.control;

import com.rhino.bjl.server.IDataMessage;
import com.rhino.bjl.utils.DateUtils;
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
    public ResponseEntity<String> searchData(HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        MediaType mediaType = new MediaType("text", "html", Charset.forName("UTF-8"));
        headers.setContentType(mediaType);
        Map<String, Object> param = new HashMap<String, Object>();
        String oneType = ParamUtils.getParameter(request, "oneType", "全部");
        String twoType = ParamUtils.getParameter(request, "twoType", "全部");
        String threeType = ParamUtils.getParameter(request, "threeType", "全部");
        String threeType4 = ParamUtils.getParameter(request, "threeType4", "全部");
        String one = ParamUtils.getParameter(request, "one", "");
        String two = ParamUtils.getParameter(request, "two", "");
        String three = ParamUtils.getParameter(request, "three", "");
        String allCount = ParamUtils.getParameter(request, "allCount", ""); //查询大局数量
        String pages = ParamUtils.getParameter(request, "pages", "1"); //查询页数
        if (threeType4.equals("庄强")) {
            threeType4="1";
        } else if (threeType4.equals("闲强")) {
            threeType4="2";
        } else if (threeType4.equals("中间")) {
            threeType4="0";
        }
        List<HashMap<String, Object>> searchData = dataMessage.findReetList2(oneType,twoType,threeType,one,two,three,allCount,pages,threeType4);
        int count = searchData.size();
        param.put("searchData", searchData);
        param.put("count", count);
        String json = JsonUtil.toJsonString(param);
        ResponseEntity<String> responseEntity = new ResponseEntity<String>(
                json, headers, HttpStatus.OK);
        return responseEntity;
    }


    @RequestMapping(value = "/initMode",method = RequestMethod.GET)
    public ModelAndView initMode() {
        ModelAndView mav = new ModelAndView("data/initMode");
        return mav;
    }

    @RequestMapping(value = "/dateCount",method = RequestMethod.GET)
    public ModelAndView dateCount() {
        ModelAndView mav = new ModelAndView("data/dataCount");
        return mav;
    }

    @RequestMapping(value = "reetAllData", method = RequestMethod.POST)
    public ResponseEntity<String> reetAllData(HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        MediaType mediaType = new MediaType("text", "html", Charset.forName("UTF-8"));
        headers.setContentType(mediaType);
        Map<String, Object> param = new HashMap<String, Object>();
        int start = ParamUtils.getIntParameter(request, "start", 0);
        int limit = ParamUtils.getIntParameter(request, "limit", 80);
        String jishu = ParamUtils.getParameter(request, "jishu", "");
        String oushu = ParamUtils.getParameter(request, "oushu", "");
        String qiang = ParamUtils.getParameter(request, "qiang", "全部");
        jishu = isNull(jishu);
        oushu = isNull(oushu);
        if (qiang.equals("庄")) {
            qiang = "1";
        } else if (qiang.equals("闲")) {
            qiang = "2";
        } else if (qiang.equals("中")){
            qiang = "0";
        }
        List<HashMap<String, Object>> yhgl = dataMessage.findReetListByMN(jishu,oushu,start, limit,qiang);
        int count = dataMessage.findReetListCountByMN(jishu,oushu,start, limit,qiang);
        param.put("yhgl", yhgl);
        param.put("count", count);
        String json = JsonUtil.toJsonString(param);
        ResponseEntity<String> responseEntity = new ResponseEntity<String>(
                json, headers, HttpStatus.OK);
        return responseEntity;
    }

    public String isNull(String param) {
        if ("N".equals(param)) {
            param = "";
        }
        return param;
    }

    @RequestMapping(value = "findReetById", method = RequestMethod.POST)
    public ResponseEntity<String> findReetById(HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        MediaType mediaType = new MediaType("text", "html", Charset.forName("UTF-8"));
        headers.setContentType(mediaType);
        Map<String, Object> param = new HashMap<String, Object>();
        String XIAN1 = ParamUtils.getParameter(request, "XIAN1", "0");
        String XIAN2 = ParamUtils.getParameter(request, "XIAN2", "0");
        String XIAN3 = ParamUtils.getParameter(request, "XIAN3", "0");
        String ZHUANG1 = ParamUtils.getParameter(request, "ZHUANG1", "0");
        String ZHUANG2 = ParamUtils.getParameter(request, "ZHUANG2", "0");
        String ZHUANG3 = ParamUtils.getParameter(request, "ZHUANG3", "0");
        String qiang = ParamUtils.getParameter(request, "qiang", "全部");
        if (qiang.equals("庄")) {
            qiang = "1";
        } else if (qiang.equals("闲")) {
            qiang = "2";
        } else if (qiang.equals("中")){
            qiang = "0";
        }
        List<HashMap<String, Object>> reetList = dataMessage.findReetByPai(XIAN1,XIAN2,XIAN3,ZHUANG1,ZHUANG2,ZHUANG3,qiang);
        param.put("reetList", reetList);
        param.put("count", reetList.size());
        String json = JsonUtil.toJsonString(param);
        ResponseEntity<String> responseEntity = new ResponseEntity<String>(
                json, headers, HttpStatus.OK);
        return responseEntity;
    }


}