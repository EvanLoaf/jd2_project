package com.gmail.evanloafakahaitao.web.command.impl;

import com.gmail.evanloafakahaitao.config.ConfigurationManager;
import com.gmail.evanloafakahaitao.config.properties.PageProperties;
import com.gmail.evanloafakahaitao.service.OrderService;
import com.gmail.evanloafakahaitao.service.impl.OrderServiceImpl;
import com.gmail.evanloafakahaitao.web.command.Command;
import com.gmail.evanloafakahaitao.web.model.CommandEnum;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteOrderCommandImpl implements Command {

    private OrderService orderService = new OrderServiceImpl();
    private ConfigurationManager configurationManager = ConfigurationManager.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String uuid = request.getParameter("uuid");
        /*int deletedOrders = orderService.deleteByUuid(uuid);
        if (deletedOrders != 0) {
            response.sendRedirect(request.getContextPath() + CommandEnum.ORDERS.getUrl());
        } else {
            request.setAttribute("error", "Could not delete order");
            return configurationManager.getProperty(PageProperties.ORDERS_PAGE_PATH);
        }*/
        return null;
    }
}
