package com.lzy.demo.okgo;

import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.lzy.demo.R;
import com.lzy.demo.base.BaseDetailActivity;
import com.lzy.demo.callback.DialogCallback;
import com.lzy.demo.callback.JsonCallback;
import com.lzy.demo.callback.JsonCallback_old;
import com.lzy.demo.callback.JsonConvert;
import com.lzy.demo.callback.StringDialogCallback;
import com.lzy.demo.model.FirstModel;
import com.lzy.demo.model.LzyResponse;
import com.lzy.demo.model.NewsListModel;
import com.lzy.demo.model.ServerModel;
import com.lzy.demo.utils.Urls;
import com.lzy.okgo.OkGo;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class JsonRequestActivity extends BaseDetailActivity {

    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_custom_request);
        ButterKnife.bind(this);
        actionBar.setTitle("自动解析Json对象");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Activity销毁时，取消网络请求
        OkGo.getInstance().cancelTag(this);
    }

    /**
     * 解析javabean对象
     */
    @OnClick(R.id.btn_test)
    public void click_btn_test(View view) {
        OkGo.get(Urls.URL_JSONOBJECT)//
            .tag(this)//ServerModel   DialogCallback
            .execute(new JsonCallback_old<LzyResponse<ServerModel>>() {
                @Override
                public void onSuccess(LzyResponse<ServerModel> firstInfo, Call call, Response response) {
                    handleResponse(firstInfo.data, call, response);
                }

                @Override
                public void onError(Call call, Response response, Exception e) {
                    super.onError(call, response, e);
                    handleError(call, response);
                }
            });
    }

    /**
     * 解析javabean对象
     */
    @OnClick(R.id.btn_test2)
    public void click_btn_test2(View view) {
        //String sUrl = "http://www.smartxuexi.com/smartmobile/main/first";

        String sUrl = "http://gank.io/api/data/%E7%A6%8F%E5%88%A9/10/1";

        OkGo.get(sUrl)//
                .tag(this)//ServerModel   DialogCallback
                .execute(new JsonCallback<NewsListModel>() {
                    //.execute(new JsonCallback_old<FirstModel>() {
                    @Override
                    public void onSuccess(NewsListModel sInfo, Call call, Response response) {
                        handleResponse(sInfo.error, call, response);

//                        Gson gson = new Gson();
//                        NewsListModel newsList = gson.fromJson(sInfo, new TypeToken<NewsListModel>() {}.getType());
//
//                        handleResponse(newsList.error, call, response);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        System.out.println("********************************");
                        System.out.println(e.getMessage());
                        System.out.println("********************************");
                        handleError(call, response);
                    }
                });
    }
//    public void click_btn_test2(View view) {
//        //String sUrl = "http://www.smartxuexi.com/smartmobile/main/first";
//
//        String sUrl = "http://gank.io/api/data/%E7%A6%8F%E5%88%A9/10/1";
//
//        OkGo.get(sUrl)//
//                .tag(this)//ServerModel   DialogCallback
//                .execute(new StringDialogCallback(this) {
//                    //.execute(new JsonCallback_old<FirstModel>() {
//                    @Override
//                    public void onSuccess(String sInfo, Call call, Response response) {
//                        //handleResponse("OK", call, response);
//
//                        Gson gson = new Gson();
//                        NewsListModel newsList = gson.fromJson(sInfo, new TypeToken<NewsListModel>() {}.getType());
//
//                        handleResponse(newsList.error, call, response);
//                    }
//
//                    @Override
//                    public void onError(Call call, Response response, Exception e) {
//                        super.onError(call, response, e);
//                        System.out.println("********************************");
//                        System.out.println(e.getMessage());
//                        System.out.println("********************************");
//                        handleError(call, response);
//                    }
//                });
//    }

//    /**
//     * 解析javabean对象
//     */
//    @OnClick(R.id.btn_test2)
//    public void click_btn_test2(View view) {
//        String sUrl = "http://www.smartxuexi.com/smartmobile/main/first";
//        OkGo.get(sUrl)//
//            .tag(this)//ServerModel   DialogCallback
//            .execute(new JsonCallback_old<FirstModel>() {
//                @Override
//                public void onSuccess(FirstModel firstInfo, Call call, Response response) {
//                    handleResponse(firstInfo.toString(), call, response);
//                }
//
//                @Override
//                public void onError(Call call, Response response, Exception e) {
//                    super.onError(call, response, e);
//                    System.out.println("********************************");
//                    System.out.println(e.getMessage());
//                    System.out.println("********************************");
//                    handleError(call, response);
//                }
//            });
//    }

    /*
    public class FirstInfo implements Serializable {

        public List<String> images;
        public List<CoverInfo> periods;
        public List<CoverInfo> syncourses;
        public List<CoverInfo> specialcourses;
        public List<CoverInfo> expandcourses;
    }

    public class CoverInfo {
        public String id;
        public String name;
        public String cover;
        public String link;
    }

 */

    public class Exam {
        //省略其它
        public String subject;
        public float grade;
    }

    public class User {
        //省略其它
        public String name;
        public int age;

        //@SerializedName("email_Address")
        @SerializedName(value = "email_address", alternate = {"emailAddress", "email"})
        public String emailAddress;

        public Exam exam;

        public List<String> images;
    }

    /**
     * 解析javabean对象
     */
    @OnClick(R.id.btn_gson)
    public void click_btn_gson(View view) {
        Gson gson = new Gson();
        String json = "{\"images\":[\"\\assets\\css\\images\\mobilebanner1.jpg\",\"\\assets\\css\\images\\mobilebanner2.jpg\"],\"periods\":[{\"id\":\"1191587\",\"name\":\"\\u751f\\u6d3b\\u4e2d\\u7684\\u7acb\\u4f53\\u56fe\\u5f62\",\"cover\":\"http:\\\\124.128.82.155\\files\\default\\2016\\07-21\\193506ac247c083990.png\",\"link\":\"\\files\\default\\2016\\07-21\\wu_1ao6jdvv01ktda609ra10go8nk0.mp4?6.1.0\"},{\"id\":\"1191589\",\"name\":\"\\u540c\\u5e95\\u6570\\u5e42\\u7684\\u4e58\\u6cd5\",\"cover\":\"http:\\\\124.128.82.155\\files\\default\\2016\\07-21\\19402377585f121667.PNG\",\"link\":\"\\files\\default\\2016\\07-21\\wu_1ao6k3ruc60q4f64qi6ci1800.mp4?6.1.0\"},{\"id\":\"1191595\",\"name\":\"\\u6b63\\u65b9\\u4f53\\u8868\\u9762\\u5c55\\u5f00\\u56fe\",\"cover\":\"http:\\\\124.128.82.155\\files\\default\\2016\\07-21\\1949459c12dd124144.png\",\"link\":\"\\files\\default\\2016\\07-21\\wu_1ao6kh1fs187p48hl4s1q33cco0.mp4?6.1.0\"},{\"id\":\"1191591\",\"name\":\"\\u70b9\\u7ebf\\u9762\\u4f53\\u4e4b\\u95f4\\u7684\\u5173\\u7cfb\",\"cover\":\"http:\\\\124.128.82.155\\files\\default\\2016\\07-21\\194607f196cc592617.png\",\"link\":\"\\files\\default\\2016\\07-21\\wu_1ao6k15vh7o28oi1f4o1ni16ph0.mp4?6.1.0\"}],\"syncourses\":[{\"id\":\"2983\",\"name\":\"\\u79d1\\u5b66\\u516d\\u5e74\\u7ea7\\u9752\\u5c9b\\u7248\\u4e0a\\u518c(\\u91d1\\u4e61\\u53bf\\u6d4e\\u5b81\\u5e02\\u4e00\\u5e08\\u4e00\\u8bfe\\u8d5b\\u4e8b\\u6d3b\\u52a8)\",\"cover\":\"http:\\\\www.smartxuexi.com\\assets\\img\\weike\\147511004530.png\"},{\"id\":\"3027\",\"name\":\"\\u6570\\u5b66\\u521d\\u4e00\\u4eba\\u6559\\u7248\\u4e0a\\u518c(\\u91d1\\u4e61\\u53bf\\u6d4e\\u5b81\\u5e02\\u4e00\\u5e08\\u4e00\\u8bfe\\u8d5b\\u4e8b\\u6d3b\\u52a8)\",\"cover\":\"http:\\\\192.168.0.213\\assets\\img\\weike\\1475110046163.png\"},{\"id\":\"650\",\"name\":\"\\u4e00\\u5e74\\u7ea7\\u8bed\\u6587\\u4eba\\u6559\\u7248\\u4e0a\\u518c\",\"cover\":\"http:\\\\124.128.82.155\\files\\default\\2016\\08-15\\08384009dc6e233539.png\"},{\"id\":\"649\",\"name\":\"\\u4e00\\u5e74\\u7ea7\\u8bed\\u6587\\u4eba\\u6559\\u7248\\u4e0b\\u518c\",\"cover\":\"http:\\\\124.128.82.155\\files\\default\\2016\\08-15\\0839004ae2b5052546.png\"}],\"specialcourses\":[{\"id\":\"364\",\"name\":\"\\u5206\\u6570\\u7684\\u521d\\u6b65\\u8ba4\\u8bc6\",\"cover\":\"http:\\\\124.128.82.155\\files\\default\\2016\\06-06\\095347b30fc6397548.png\"},{\"id\":\"427\",\"name\":\"\\u57fa\\u56e0\",\"cover\":\"http:\\\\124.128.82.155\\files\\default\\2016\\06-06\\140738adedef806368.png\"},{\"id\":\"371\",\"name\":\"\\u5c0f\\u5b66\\u53e4\\u8bd7\\u6587\\u5b66\\u4e60\\u7591\\u96be\\u5fae\\u8bfe\\u7a0b\",\"cover\":\"http:\\\\124.128.82.155\\files\\default\\2016\\06-06\\1046571df7c2932744.png\"},{\"id\":\"441\",\"name\":\"\\u6279\\u6ce8\\u9605\\u8bfb\",\"cover\":\"http:\\\\124.128.82.155\\files\\default\\2016\\06-06\\1444386f23bb253209.png\"}],\"expandcourses\":[{\"id\":\"536\",\"name\":\"\\u8fdc\\u79bb\\u6bd2\\u54c1\\uff0c\\u9633\\u5149\\u751f\\u6d3b\",\"cover\":\"http:\\\\124.128.82.155\\files\\default\\2016\\06-08\\09362262ade9701974.png\"},{\"id\":\"535\",\"name\":\"\\u8fdc\\u79bb\\u8d4c\\u535a\\uff0c\\u4ece\\u6211\\u505a\\u8d77\\uff01\",\"cover\":\"http:\\\\124.128.82.155\\files\\default\\2016\\06-08\\0926499149ad563043.png\"},{\"id\":\"534\",\"name\":\"\\u5065\\u5eb7\\u4e0a\\u7f51\\uff0c\\u6587\\u660e\\u4e0a\\u7f51\",\"cover\":\"http:\\\\124.128.82.155\\files\\default\\2016\\06-08\\090812c3b602447900.png\"},{\"id\":\"510\",\"name\":\"\\u5b89\\u5168\\u6559\\u80b2-\\u9884\\u9632\\u81ea\\u7136\\u707e\\u5bb3\\u5e38\\u8bc6\",\"cover\":\"http:\\\\124.128.82.155\\files\\default\\2016\\06-07\\141225995c18618407.png\"}]}\n";
        FirstModel firstInfo = gson.fromJson(json, new TypeToken<FirstModel>() {}.getType());
        System.out.println("expandcourses - " + firstInfo.expandcourses.size());
        System.out.println("expandcourses - " + firstInfo.expandcourses);
        System.out.println("expandcourses - " + firstInfo.expandcourses.get(0).id);
        System.out.println("expandcourses - " + firstInfo.expandcourses.get(0).name);
        System.out.println("expandcourses - " + firstInfo.expandcourses.get(0).cover);
        System.out.println("expandcourses - " + firstInfo.expandcourses.get(0).link);

        System.out.println("------------------------------------");

        System.out.println("images - " + firstInfo.images.size());
        System.out.println("images - " + firstInfo.images);

        System.out.println("====================================");

        //String json = "{\"name\":\"怪盗kidou\",\"age\":24,\"email_Address\":\"ikidou_1@example.com\",\"email\":\"ikidou_2@example.com\",\"email_address\":\"ikidou_3@example.com\"}";
        json = "{name:怪盗kidou,\"age\":24,\"email\":\"ikidou_2@example.com\", exam: {subject: \"语文\", grade: 98.7} , images: [\"Android\",\"Java\",\"PHP\"]}";
        User user = gson.fromJson(json, User.class);
        System.out.println("name - " + user.name);
        System.out.println("age - " + user.age);
        System.out.println("emailAddress - " + user.emailAddress);
        System.out.println("images - " + user.images);


        System.out.println("=================================");


//
//        String jsonArray = "[\"Android\",\"Java\",\"PHP\"]";
//        String[] strings = gson.fromJson(jsonArray, String[].class);
//        List<String> stringList = gson.fromJson(jsonArray, new TypeToken<List<String>>() {}.getType());
//        System.out.println("count - " + strings.length);
//        System.out.println("list - " + strings);
//        System.out.println("------------------------------------");
//        System.out.println("count - " + stringList.size());
//        System.out.println("list - " + stringList);


//        String sUrl = "http://www.smartxuexi.com/smartmobile/main/first";
//
//        OkGo.get(sUrl)//
//                .tag(this)//
//                .execute(new DialogCallback<LzyResponse<ServerModel>>(this) {
//                    @Override
//                    public void onSuccess(LzyResponse<ServerModel> responseData, Call call, Response response) {
//                        handleResponse(responseData.data, call, response);
//                    }
//
//                    @Override
//                    public void onError(Call call, Response response, Exception e) {
//                        super.onError(call, response, e);
//                        handleError(call, response);
//                    }
//                });
    }

    /**
     * 解析javabean对象
     */
    @OnClick(R.id.requestJson)
    public void requestJson(View view) {
        OkGo.get(Urls.URL_JSONOBJECT)//
                .tag(this)//
                .headers("header1", "headerValue1")//
                .params("param1", "paramValue1")//
                .execute(new DialogCallback<LzyResponse<ServerModel>>(this) {
                    @Override
                    public void onSuccess(LzyResponse<ServerModel> responseData, Call call, Response response) {
                        handleResponse(responseData.data, call, response);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        handleError(call, response);
                    }
                });
    }

    /**
     * 解析集合对象
     */
    @OnClick(R.id.requestJsonArray)
    public void requestJsonArray(View view) {
        OkGo.get(Urls.URL_JSONARRAY)//
                .tag(this)//
                .headers("header1", "headerValue1")//
                .params("param1", "paramValue1")//
                .execute(new DialogCallback<LzyResponse<List<ServerModel>>>(this) {
                    @Override
                    public void onSuccess(LzyResponse<List<ServerModel>> responseData, Call call, Response response) {
                        handleResponse(responseData.data, call, response);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        handleError(call, response);
                    }
                });
    }


}
