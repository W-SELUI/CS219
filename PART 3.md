
Create a new one SVM02

network : host only for now then start it up

use same admin password : `P@$$w0rd12345`

change ip and also default gateway to 10.0.0.50

ncpa.cpl right click, rename ethernet to internal (btw this is the host-only)

check using cmd

local server on server manager

click on computer name -> change -> S11244878 -> restart

go to powershell, type hostname -> should be ID

power off, go to setting -> network -> choose bridge then start it up

log in  -> ncpa.cpl -> change other one to external (btw this is the bridge)

ping 10.0.0.50 

nslookup domain

server manager -> local server -> workgroup -> change domain to S11244878.local -> username : s11244878\Administrator -> Password  :  `P@$$w0rd12345`  -> restart

log in again -> local server -> domain is there -> computer name is there as well

server manager -> add role and ... -> next to server roles -> click remote access -> next to roles servcices tick both -> next install -> tools -> routing remote access -> show/hide action -> more action for the VM -> configure -> next -> custom,..... -> VPN access LAN routing -> start service -> right click -> Properties -> IPV4 -> start 10.0.0.100 end 10.0.0.110 

right click port -> properties -> miniport(PPTP) -> configure -> apply, ok

then so on.... tutor was really fast could take note of everything 
