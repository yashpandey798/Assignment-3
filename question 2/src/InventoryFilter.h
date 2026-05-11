#ifndef INVENTORY_FILTER_H
#define INVENTORY_FILTER_H

#include "InventoryItem.h"
#include <vector>
#include <string>
#include <stdexcept>

// Custom exceptions
class MissingFilterCriteriaException : public std::exception {
public:
    const char* what() const noexcept override {
        return "ERROR: Filter criteria is missing! Please provide: Memory, CPU, Linux, or Windows";
    }
};

class InvalidFilterCriteriaException : public std::exception {
private:
    std::string msg;
public:
    InvalidFilterCriteriaException(const std::string& criteria) {
        msg = "ERROR: Invalid filter criteria '" + criteria + "'. Valid options: Memory, CPU, Linux, Windows";
    }
    const char* what() const noexcept override {
        return msg.c_str();
    }
};

/**
 * Core class that filters inventory based on criteria.
 */
class InventoryFilter {
public:
    // Returns max Memory item
    InventoryItem filterByMaxMemory(const std::vector<InventoryItem>& items) const;

    // Returns max CPU item
    InventoryItem filterByMaxCPU(const std::vector<InventoryItem>& items) const;

    // Returns all items matching given OS type
    std::vector<InventoryItem> filterByOS(const std::vector<InventoryItem>& items, const std::string& osType) const;

    // Main filter method — calls above based on criteria
    void applyFilter(const std::vector<InventoryItem>& items, const std::string& criteria) const;
};

#endif
