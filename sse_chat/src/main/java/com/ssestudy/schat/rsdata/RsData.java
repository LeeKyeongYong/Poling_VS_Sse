package com.ssestudy.schat.rsdata;

import lombok.AllArgsConstructor;
import lombok.Getter;
@AllArgsConstructor
@Getter
public class RsData<T> {
    private String resultCode;
    private String msg;
    private T data;
}
