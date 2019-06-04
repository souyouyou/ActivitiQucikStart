package com.krxinuo.util.response;

/**
 * 响应数据封装类
 */
public class ResultViewModelUtil {
    /**
     * 请求成功方法01
     * @param object 响应数据
     * @return 视图模型实例
     */
    public static ResultViewModel success(Object object) {
        ResultViewModel resultViewModel = new ResultViewModel();
        resultViewModel.setCode(Code.SUCCESS.getCode());
        resultViewModel.setMessage("请求成功");
        resultViewModel.setData(object);
        return resultViewModel;
    }

    /**
     * 请求成功方法02
     * @return 视图模型实例
     */
    public static ResultViewModel success() {
        return success(null);
    }

    /**
     * 请求成功方法03
     * @param object 响应数据
     * @param extraData 额外数据
     * @return
     */
    public static ResultViewModel success(Object object,Object extraData) {
        ResultViewModel resultViewModel = new ResultViewModel();
        resultViewModel.setCode(Code.SUCCESS.getCode());
        resultViewModel.setMessage("请求成功");
        resultViewModel.setData(object);
        resultViewModel.setExtraData(extraData);
        return resultViewModel;
    }

    /**
     * 请求方法成功
     * @param redirectUrl 重定向地址
     * @return
     */
    public static ResultViewModel redirect(String redirectUrl) {
        ResultViewModel resultViewModel = new ResultViewModel();
        resultViewModel.setCode(Code.SUCCESS.getCode());
        resultViewModel.setMessage("请求成功");
        resultViewModel.setRedirectUrl(redirectUrl);
        return resultViewModel;
    }


    public static ResultViewModel refresh() {
        ResultViewModel resultViewModel = new ResultViewModel();
        resultViewModel.setCode(Code.SUCCESS.getCode());
        resultViewModel.setMessage("请求成功");
        resultViewModel.setRefresh(true);
        return resultViewModel;
    }

    /**
     * 请求失败方法01（捕获到的已知异常）
     * @param code 异常编号
     * @param message 异常信息
     * @return 视图模型实例
     */
    public static ResultViewModel error(Integer code, String message) {
        ResultViewModel resultViewModel = new ResultViewModel();
        resultViewModel.setCode(code);
        resultViewModel.setMessage(message);
        resultViewModel.setData(null);
        return resultViewModel;
    }

    /**
     * 请求失败方法02（系统异常）
     * @return 视图模型实例
     */
    public static ResultViewModel error() {
        ResultViewModel resultViewModel = new ResultViewModel();
        resultViewModel.setCode(Code.FAIL.getCode());
        resultViewModel.setMessage("系统异常");
        resultViewModel.setData("系统维护中...");
        return resultViewModel;
    }





}