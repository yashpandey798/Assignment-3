#include "InventoryFilter.h"
#include <iostream>
#include <algorithm>
#include <cctype>

// Helper: convert string to lowercase
static std::string toLower(const std::string& s) {
    std::string result = s;
    std::transform(result.begin(), result.end(), result.begin(), ::tolower);
    return result;
}

/**
 * Returns the item with maximum memory
 */
InventoryItem InventoryFilter::filterByMaxMemory(const std::vector<InventoryItem>& items) const {
    InventoryItem maxItem = items[0];
    for (const auto& item : items) {
        if (item.getMemoryGB() > maxItem.getMemoryGB()) {
            maxItem = item;
        }
    }
    return maxItem;
}

/**
 * Returns the item with maximum CPU speed
 */
InventoryItem InventoryFilter::filterByMaxCPU(const std::vector<InventoryItem>& items) const {
    InventoryItem maxItem = items[0];
    for (const auto& item : items) {
        if (item.getCpuGhz() > maxItem.getCpuGhz()) {
            maxItem = item;
        }
    }
    return maxItem;
}

/**
 * Returns all items matching given OS type (Linux/Windows)
 */
std::vector<InventoryItem> InventoryFilter::filterByOS(const std::vector<InventoryItem>& items, const std::string& osType) const {
    std::vector<InventoryItem> result;
    for (const auto& item : items) {
        if (toLower(item.os) == toLower(osType)) {
            result.push_back(item);
        }
    }
    return result;
}

/**
 * Main filter method — validates criteria and applies correct filter
 */
void InventoryFilter::applyFilter(const std::vector<InventoryItem>& items, const std::string& criteria) const {
    std::string lc = toLower(criteria);

    std::cout << "=======================================================" << std::endl;
    std::cout << " Inventory Filter Results" << std::endl;
    std::cout << " Filter Criteria : " << criteria << std::endl;
    std::cout << "=======================================================" << std::endl;

    if (lc == "memory") {
        InventoryItem result = filterByMaxMemory(items);
        std::cout << "Item with MAX Memory:" << std::endl;
        result.display();

    } else if (lc == "cpu") {
        InventoryItem result = filterByMaxCPU(items);
        std::cout << "Item with MAX CPU:" << std::endl;
        result.display();

    } else if (lc == "linux" || lc == "windows") {
        std::vector<InventoryItem> results = filterByOS(items, criteria);
        std::cout << "Items with OS = " << criteria << " (" << results.size() << " found):" << std::endl;
        int count = 1;
        for (const auto& item : results) {
            std::cout << "--- Item " << count++ << " ---" << std::endl;
            item.display();
        }
        if (results.empty()) {
            std::cout << "No items found for OS: " << criteria << std::endl;
        }

    } else {
        throw InvalidFilterCriteriaException(criteria);
    }

    std::cout << "=======================================================" << std::endl;
}
