#ifndef INVENTORY_ITEM_H
#define INVENTORY_ITEM_H

#include <string>

/**
 * Model class representing a single inventory item (one server/machine).
 */
class InventoryItem {
public:
    std::string ip;
    std::string os;
    std::string memory;   // e.g. "4GB"
    std::string cpu;      // e.g. "3.8Ghz"
    std::string disk;

    double getMemoryGB() const;   // converts "4GB" -> 4.0
    double getCpuGhz() const;     // converts "3.8Ghz" -> 3.8

    void display() const;
};

#endif
