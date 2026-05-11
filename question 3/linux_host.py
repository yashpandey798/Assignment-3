"""
linux_host.py
LinuxHost class — gets hardware info using Linux OS commands.
"""

import subprocess
import socket
from host_info import HostInfo


class LinuxHost(HostInfo):
    """
    Fetches hardware info on Linux systems using shell commands.
    """

    def get_hardware_info(self):
        """
        Uses Linux commands to get hardware details:
        - hostname : socket.gethostname()
        - memory   : free -h
        - cpu      : lscpu
        - ip       : hostname -I
        - disk     : df -h
        """
        # --- Hostname ---
        self.hostname = socket.gethostname()

        # --- IP Address ---
        try:
            result = subprocess.run(
                ["hostname", "-I"],
                capture_output=True, text=True
            )
            self.ip = result.stdout.strip().split()[0]
        except Exception:
            self.ip = socket.gethostbyname(self.hostname)

        # --- Memory (Total RAM) ---
        try:
            result = subprocess.run(
                ["free", "-h"],
                capture_output=True, text=True
            )
            # Line format: Mem: total used free ...
            for line in result.stdout.splitlines():
                if line.startswith("Mem:"):
                    parts = line.split()
                    self.memory = parts[1] + " (Total RAM)"
                    break
        except Exception as e:
            self.memory = "Unable to fetch: " + str(e)

        # --- CPU Info ---
        try:
            result = subprocess.run(
                ["lscpu"],
                capture_output=True, text=True
            )
            cpu_info = {}
            for line in result.stdout.splitlines():
                if "Model name" in line or "CPU MHz" in line or "CPU(s)" in line:
                    parts = line.split(":")
                    if len(parts) == 2:
                        cpu_info[parts[0].strip()] = parts[1].strip()
            self.cpu = cpu_info if cpu_info else result.stdout[:200]
        except Exception as e:
            self.cpu = "Unable to fetch: " + str(e)

        # --- Disk Size ---
        try:
            result = subprocess.run(
                ["df", "-h", "/"],
                capture_output=True, text=True
            )
            lines = result.stdout.splitlines()
            if len(lines) > 1:
                parts = lines[1].split()
                self.disk_size = parts[1] + " (Total) / " + parts[3] + " (Available)"
        except Exception as e:
            self.disk_size = "Unable to fetch: " + str(e)
