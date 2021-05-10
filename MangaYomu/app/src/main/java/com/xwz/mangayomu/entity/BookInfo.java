package com.xwz.mangayomu.entity;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.File;

public class BookInfo implements Parcelable {
    private String bookName;
    private int pageNum;
    private Uri coverUri;
    private String bookDir;

    public BookInfo(){}

    protected BookInfo(Parcel in) {
        bookName = in.readString();
        pageNum = in.readInt();
        coverUri = in.readParcelable(Uri.class.getClassLoader());
        bookDir = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(bookName);
        dest.writeInt(pageNum);
        dest.writeParcelable(coverUri, flags);
        dest.writeString(bookDir);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BookInfo> CREATOR = new Creator<BookInfo>() {
        @Override
        public BookInfo createFromParcel(Parcel in) {
            return new BookInfo(in);
        }

        @Override
        public BookInfo[] newArray(int size) {
            return new BookInfo[size];
        }
    };

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public Uri getCoverUri() {
        return coverUri;
    }

    public void setCoverUri(Uri coverUri) {
        this.coverUri = coverUri;
    }

    public String getBookDir() {
        return bookDir;
    }

    public void setBookDir(String bookDir) {
        this.bookDir = bookDir;
    }

    @Override
    public String toString() {
        return "BookInfo{" +
                "bookName='" + bookName + '\'' +
                ", pageNum=" + pageNum +
                ", coverUri=" + coverUri +
                ", bookDir='" + bookDir + '\'' +
                '}';
    }
}
