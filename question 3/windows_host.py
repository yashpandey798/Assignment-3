"""
windows_host.py
WindowsHost class — gets hardware info using PowerShell commands.
(wmic is deprecated on Windows 11, using Get-CimInstance instead)
"""

import subprocess
import socket
from host_info import HostInfo


class WindowsHost(HostInfo):
    """
    Fetches hardware info on Windows systems using PowerShell CimInstance commands.
    """

    def _run_ps(self, command):
        """Helper to run a PowerShell command and return output string."""
        result = subprocess.run(
            ["powershell", "-Command", command],
            capture_output=True, text=True
        )
        return result.stdout.strip()

    def get_hardware_info(self):
        # --- Hostname ---
        self.hostname = socket.gethostname()

        # --- IP Address ---
        try:
            self.ip = socket.gethostbyname(self.hostname)
        except Exception as e:
            self.ip = "Unable to fetch: " + str(e)

        # --- Memory (Total RAM KB -> GB) ---
        try:
            output = self._run_ps("(Get-CimInstance Win32_OperatingSystem).TotalVisibleMemorySize")
            kb = int(output.strip())
            gb = round(kb / (1024 * 1024), 2)
            self.memory = str(gb) + " GB (Total RAM)"
        except Exception as e:
            self.memory = "Unable to fetch: " + str(e)

        # --- CPU Info ---
        try:
            output = self._run_ps("(Get-CimInstance Win32_Processor).Name")
            self.cpu = output.strip()
        except Exception as e:
            self.cpu = "Unable to fetch: " + str(e)

        # --- Disk Size ---
        try:
            output = self._run_ps(
                "(Get-CimInstance Win32_DiskDrive | Measure-Object -Property Size -Sum).Sum"
            )
            total_bytes = int(float(output.strip()))
            gb = round(total_bytes / (1024 ** 3), 2)
            self.disk_size = str(gb) + " GB (Total Disk)"
        except Exception as e:
            self.disk_size = "Unable to fetch: " + str(e)
