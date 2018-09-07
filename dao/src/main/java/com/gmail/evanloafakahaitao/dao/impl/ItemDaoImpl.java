package com.gmail.evanloafakahaitao.dao.impl;

import com.gmail.evanloafakahaitao.dao.ItemDao;
import com.gmail.evanloafakahaitao.dao.model.Item;
import com.gmail.evanloafakahaitao.dao.util.ItemConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDaoImpl extends GenericDaoImpl<Item> implements ItemDao {

    private static final Logger logger = LogManager.getLogger(ItemDaoImpl.class);

    private ItemConverter itemConverter = new ItemConverter();

    public ItemDaoImpl(Class<Item> clazz) {
        super(clazz);
    }

    @Override
    public int save(Connection connection, List<Item> itemList) {
        String saveItem = "insert into item(name, vendor_code, description, price) " +
                "values(?, ?, ?, ?) on duplicate key update vendor_code = vendor_code";
        int changedRows = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement(saveItem)) {
            for (Item item : itemList) {
                preparedStatement.setString(1, item.getName());
                preparedStatement.setLong(2, item.getVendorCode());
                preparedStatement.setString(3, item.getDescription());
                preparedStatement.setBigDecimal(4, item.getPrice());
                preparedStatement.addBatch();
            }
            int[] changedRowsBatchArray = preparedStatement.executeBatch();
            for (int i : changedRowsBatchArray) {
                if (changedRows == 0) {
                    changedRows = i;
                } else {
                    changedRows += i;
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return changedRows;
    }

    @Override
    public List<Item> findAll(Connection connection) {
        String findAllItems = "select i.id as item_id, i.name as item_name, i.vendor_code, i.description, i.price from item i";
        List<Item> itemList = new ArrayList<>();
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(findAllItems);
                ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            while (resultSet.next()) {
                Item item = itemConverter.toItem(resultSet);
                itemList.add(item);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return itemList;
    }

    @Override
    public Item findByVendorCode(Connection connection, Long vendorCode) {
        String findByVendorCode = "select i.id as item_id, i.name as item_name, i.vendor_code, i.description, i.price from item i where i.vendor_code = ?";
        Item item = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(findByVendorCode)) {
            preparedStatement.setLong(1, vendorCode);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    item = itemConverter.toItem(resultSet);
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return item;
    }

    @Override
    public Item findById(Connection connection, Long itemId) {
        String findById = "select i.id as item_id, i.name as item_name, i.vendor_code, i.description, i.price from item i where i.id = ?";
        Item item = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(findById)) {
            preparedStatement.setLong(1, itemId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    item = itemConverter.toItem(resultSet);
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return item;
    }
}