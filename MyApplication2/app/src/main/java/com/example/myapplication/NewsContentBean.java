package com.example.myapplication;

import java.util.List;

/**
 * Created by 上官轩明 on 2017/11/7.
 */

public class NewsContentBean {

    /**
     * error_code : -1
     * message :
     * data : {"index":"15092","subject":"共青团天津大学代表会议胜利召开","content":"<style>img{width: 100% !important;margin:0 auto !important;}<\/style><p>&nbsp;&nbsp;&nbsp;&nbsp;2008年3月14日下午16:00共青团天津大学代表会议在大学生活动中心204室隆重举行。校团委书记罗进飞同志，校团委副书记王阳同志，共青团天津大学第八届委员会委员，天津大学各级团干部、团员代表参加了会议。会议由校团委副书记吕静主持。本次会议的主要任务是增选共青团天津大学第八届委员会委员。<\/p>\r\n<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<\/p>\r\n<p align=\"center\"><img height=\"163\" alt=\"\" src=\"/news/uploadfile/Image/200803/2/08031502.JPG\" width=\"266\" align=\"absBottom\" /><\/p>\r\n<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <\/p>\r\n<p><br />&nbsp;&nbsp;&nbsp;&nbsp;此前，共青团天津大学第八届委员会召开第三次全体会议，会议通过了代表资格审查报告，选举办法和总监票人、监票人名单，确定了候选人名单。本次团员代表会议应到团员代表97名，实到92名，通过差额选举增补了13 名委员，他们是：于庆雷、王用源、文莉（女）、刘岩（女）、宋立斌、李哲、李春意、赵剑、徐磊、黄静（女）、崔红（女）、解晶（女）、薛毅。团员代表会议结束后，召开第四次全委会增选常委，有4名委员当选，他们是：王葳（女）、曲海富、李京霖（女）、李磊。<br />&nbsp;&nbsp;&nbsp;&nbsp;校团委书记罗进飞同志发表了热情洋溢的讲话，以&ldquo;一二三四五六&rdquo;精辟总结了我校共青团工作的特色，并对广大团员青年提出了殷切的期望。与会团员青年纷纷表示要深入思考自己肩负的责任与使命，不辜负团组织的期望与重托，为我校共青团事业发展贡献力量。<\/p>\r\n<p>&nbsp;<\/p>\r\n<p>供稿：校团委<br />审稿：天外天新闻中心 杨依<\/p>","newscome":"校团委","gonggao":"","shengao":"","sheying":"","visitcount":240,"comments":[]}
     */

    private int error_code;
    private String message;
    private DataBean data;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * index : 15092
         * subject : 共青团天津大学代表会议胜利召开
         * content : <style>img{width: 100% !important;margin:0 auto !important;}</style><p>&nbsp;&nbsp;&nbsp;&nbsp;2008年3月14日下午16:00共青团天津大学代表会议在大学生活动中心204室隆重举行。校团委书记罗进飞同志，校团委副书记王阳同志，共青团天津大学第八届委员会委员，天津大学各级团干部、团员代表参加了会议。会议由校团委副书记吕静主持。本次会议的主要任务是增选共青团天津大学第八届委员会委员。</p>
         <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</p>
         <p align="center"><img height="163" alt="" src="/news/uploadfile/Image/200803/2/08031502.JPG" width="266" align="absBottom" /></p>
         <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </p>
         <p><br />&nbsp;&nbsp;&nbsp;&nbsp;此前，共青团天津大学第八届委员会召开第三次全体会议，会议通过了代表资格审查报告，选举办法和总监票人、监票人名单，确定了候选人名单。本次团员代表会议应到团员代表97名，实到92名，通过差额选举增补了13 名委员，他们是：于庆雷、王用源、文莉（女）、刘岩（女）、宋立斌、李哲、李春意、赵剑、徐磊、黄静（女）、崔红（女）、解晶（女）、薛毅。团员代表会议结束后，召开第四次全委会增选常委，有4名委员当选，他们是：王葳（女）、曲海富、李京霖（女）、李磊。<br />&nbsp;&nbsp;&nbsp;&nbsp;校团委书记罗进飞同志发表了热情洋溢的讲话，以&ldquo;一二三四五六&rdquo;精辟总结了我校共青团工作的特色，并对广大团员青年提出了殷切的期望。与会团员青年纷纷表示要深入思考自己肩负的责任与使命，不辜负团组织的期望与重托，为我校共青团事业发展贡献力量。</p>
         <p>&nbsp;</p>
         <p>供稿：校团委<br />审稿：天外天新闻中心 杨依</p>
         * newscome : 校团委
         * gonggao :
         * shengao :
         * sheying :
         * visitcount : 240
         * comments : []
         */

        private String index;
        private String subject;
        private String content;
        private String newscome;
        private String gonggao;
        private String shengao;
        private String sheying;
        private int visitcount;
        private List<?> comments;

        public String getIndex() {
            return index;
        }

        public void setIndex(String index) {
            this.index = index;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getNewscome() {
            return newscome;
        }

        public void setNewscome(String newscome) {
            this.newscome = newscome;
        }

        public String getGonggao() {
            return gonggao;
        }

        public void setGonggao(String gonggao) {
            this.gonggao = gonggao;
        }

        public String getShengao() {
            return shengao;
        }

        public void setShengao(String shengao) {
            this.shengao = shengao;
        }

        public String getSheying() {
            return sheying;
        }

        public void setSheying(String sheying) {
            this.sheying = sheying;
        }

        public int getVisitcount() {
            return visitcount;
        }

        public void setVisitcount(int visitcount) {
            this.visitcount = visitcount;
        }

        public List<?> getComments() {
            return comments;
        }

        public void setComments(List<?> comments) {
            this.comments = comments;
        }
    }
}
