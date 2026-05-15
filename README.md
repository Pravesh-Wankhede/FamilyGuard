# Family Protector 🛡️

A real-time family safety Android app I built solo — lets family members share live location, stay connected, and feel safer without the clutter of commercial tracking apps.

---


## What It Does

- **Live Location Sharing** — Real-time GPS tracking using FusedLocationProvider, updates every 5 seconds
- **Google Sign-In** — One-tap login, no passwords to remember
- **Family Map View** — See family members pinned on a Google Map, live
- **Persistent Login** — Stay logged in across app restarts
- **Firebase Backend** — Auth and Firestore for real-time data sync

---

## Tech Stack

| What | How |
|------|-----|
| Language | Kotlin |
| UI | XML Layouts + Jetpack Compose (mixed) |
| Auth | Firebase Auth + Google Credential Manager |
| Database | Firebase Firestore |
| Maps | Google Maps SDK |

---


## Permissions Required

```xml
ACCESS_FINE_LOCATION
ACCESS_COARSE_LOCATION
INTERNET
CAMERA
READ_CONTACTS
```

---

## What I Learned

Honestly this project taught me more than I expected — especially around the new Credential Manager API (which replaced the old Google Sign-In flow and has very little documentation), handling live location updates properly without draining battery, and structuring a Firebase-backed app from scratch without a tutorial holding my hand.

---

## Known Limitations / What's Next

- [ ] Push notifications when a family member reaches a location
- [ ] Geofencing alerts
- [ ] Multiple family members on the same map
- [ ] Better UI (rushed the design to get the functionality working first)

---

Built by **Pravesh** — open to feedback, contributions, or just a conversation about Android dev.
