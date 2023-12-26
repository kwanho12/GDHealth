<%--
  Created by IntelliJ IDEA.
  User: 정인호
  Date: 2023-12-26
  Time: 오전 11:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/view/branch/include/debug.jsp"/>
<!doctype html>
<html class="no-js" lang="ko">

<head>
    <jsp:include page="/WEB-INF/view/branch/include/head.jsp"/>
</head>

<body>
    <jsp:include page="/WEB-INF/view/branch/include/body-upper-layout.jsp"/>
    <div class="breadcome-area">
        <div class="container-fluid">
            <div class="row">
                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                    <div class="breadcome-list single-page-breadcome">
                        <div class="row">
                            <div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
                                <div class="breadcome-heading">
                                </div>
                            </div>
                            <div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="single-pro-review-area mt-t-50 mg-b-15">
        <div class="container-fluid">
            <div class="row">
                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                    <div class="product-payment-inner-st">
                        <ul id="myTabedu1" class="tab-review-design">
                            <li class="active"><a href="#description">Courses Details</a></li>
                        </ul>
                        <div id="myTabContent" class="tab-content custom-product-edit">
                            <div class="product-tab-list tab-pane fade active in" id="description">
                                <div class="row">
                                    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                                        <div class="review-content-section">
                                            <div id="dropzone1" class="pro-ad addcoursepro">
                                                <form action="#" class="dropzone dropzone-custom needsclick add-professors dz-clickable" id="demo1-upload">
                                                    <div class="row">
                                                        <div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
                                                            <div class="form-group">
                                                                <label>발주자</label>
                                                                <input name="number" type="text" class="form-control" placeholder="Course Name" value="Apps Development">
                                                            </div>
                                                            <div class="form-group">
                                                                <label>발주자</label>
                                                                <input type="text" class="form-control" placeholder="Course Start Date" value="12/10/2017">
                                                            </div>
                                                            <div class="form-group">
                                                                <label>발주자</label>
                                                                <input type="text" class="form-control" placeholder="Course Duration" value="6 Months">
                                                            </div>
                                                            <div class="form-group">
                                                                <label>발주자</label>
                                                                <input type="text" class="form-control" placeholder="Course Price" value="$400">
                                                            </div>
                                                            <div class="form-group alert-up-pd">
                                                                <div class="dz-message needsclick download-custom">
                                                                    <i class="fa fa-download edudropnone" aria-hidden="true"></i>
                                                                    <h2 class="edudropnone">Drop image here or click to upload.</h2>
                                                                    <p class="edudropnone"><span class="note needsclick">(This is just a demo dropzone. Selected image is <strong>not</strong> actually uploaded.)</span>
                                                                    </p>
                                                                    <input name="imageico" class="hd-pro-img" type="text">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
                                                            <div class="form-group res-mg-t-15">
                                                                <input type="text" class="form-control" placeholder="Department" value="CSE">
                                                            </div>
                                                            <div class="form-group edit-ta-resize">
                                                                <textarea name="description">Lorem ipsum dolor sit amet of, consectetur adipiscing elitable. Vestibulum tincidunt est vitae ultrices accumsan.</textarea>
                                                            </div>
                                                            <div class="form-group">
                                                                <input type="text" class="form-control" placeholder="Course Professor" value="Selima sha">
                                                            </div>
                                                            <div class="form-group">
                                                                <input type="text" class="form-control" placeholder="Year" value="1 Year">
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-lg-12">
                                                            <div class="payment-adress">
                                                                <button type="submit" class="btn btn-primary waves-effect waves-light">Submit</button>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

        <!-- 본문 종료 -->
    <jsp:include page="/WEB-INF/view/branch/include/body-lower-layout.jsp"/>
</body>

</html>