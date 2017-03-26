package edu.libsys.service;

import edu.libsys.data.dao.*;
import edu.libsys.entity.Book;
import edu.libsys.entity.Paper;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.ext.Provider;
import java.util.List;

@Path("recommend")
@Provider
@Produces({"application/x-javascript;charset=UTF-8", "application/json;charset=UTF-8"})
public class RecommendResource {

    //Paper数据访问对象
    private final PaperDao paperDao = new PaperDao();
    //Book数据访问对象
    private final BookDao bookDao = new BookDao();

    //获取图书资源
    @Path("books")
    @GET
    public List<Book> getRecommendBookList(@QueryParam("id") final int id, @QueryParam("type") final String type) {
        List<Book> bookList = null;
        if (type.equals("paper")) {
            //根据论文ID获取图书推荐资源
            PaperBookRelationshipDao paperBookRelationshipDao = new PaperBookRelationshipDao();
            bookList = bookDao.getBookListByIdList(paperBookRelationshipDao.getRecommendBookIdListByPaperId(id));
        } else if (type.equals("book")) {
            //根据图书ID获取图书推荐资源
            BookBookRelationshipDao bookBookRelationshipDao = new BookBookRelationshipDao();
            bookList = bookDao.getBookListByIdList(bookBookRelationshipDao.getRecommendBookList(id));
        }
        return bookList;
    }

    //获取论文资源
    @Path("papers")
    @GET
    public List<Paper> getRecommendPaperList(@QueryParam("id") final int id, @QueryParam("type") final String type) {
        List<Paper> paperList = null;
        if (type.equals("paper")) {
            //根据论文ID获取论文推荐
            PaperPaperRelationshipDao paperPaperRelationshipDao = new PaperPaperRelationshipDao();
            paperList = paperDao.getPaperListByIdList(paperPaperRelationshipDao.getRecommendPaperIdList(id));
        } else if (type.equals("book")) {
            //根据图书ID获取论文推荐
            PaperBookRelationshipDao paperBookRelationshipDao = new PaperBookRelationshipDao();
            paperList = paperDao.getPaperListByIdList(paperBookRelationshipDao.getRecommendBookIdListByPaperId(id));
        }
        return paperList;
    }
}