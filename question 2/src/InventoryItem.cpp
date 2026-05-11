#include "InventoryItem.h"
#include <iostream>
#include <stdexcept>

/**
 * Converts memory string like "4GB" or "16GB" to double value 4.0 / 16.0
 */
double InventoryItem::getMemoryGB() const {
    std::string val = memory;
    // Remove "GB" suffix (case insensitive)
    size_t pos = val.find("GB");
    if (pos == std::string::npos) pos = val.find("gb");
    if (pos != std::string::npos) val = val.substr(0, pos);
    return std::stod(val);
}

/**
 * Converts CPU string like "3.8Ghz" or "1Ghz" to double value
 */
double InventoryItem::getCpuGhz() const {
    std::string val = cpu;
    // Remove "Ghz" or "GHz" suffix
    size_t pos = val.find("Ghz");
    if (pos == std::string::npos) pos = val.find("GHz");
    if (pos == std::string::npos) pos = val.find("ghz");
    if (pos != std::string::npos) val = val.substr(0, pos);
    return std::stod(val);
}

/**
 * Display inventory item details
 */
void InventoryItem::display() const {
    std::cout << "  IP     : " << ip     << std::endl;
    std::cout << "  OS     : " << os     << std::endl;
    std::cout << "  Memory : " << memory << std::endl;
    std::cout << "  CPU    : " << cpu    << std::endl;
    std::cout << "  Disk   : " << disk   << std::endl;
}
