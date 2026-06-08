## v9.0.2, 02 June 2026
- Added `lang` parameter support in Autosuggest and Nearby APIs.
- Added Reverse Geocode Nearby API support.
- Added `validIndication` field in` IntersectionLanes` within the Routing response.
- Fixed an issue related to `MapSnapshotter`.

## v9.0.1, 31 March 2026
- Fix `_` support in package Name
- Remove `hyperLocal` & `zoom` from Autosugget Request.
- Remove `user` & `score` from Autosuggest Response.
- Added `searchType` in Autosuggest, Nearby & Reverse Geocode 
- Added `bounds`, `bridge` and `refLocation` in Poi Along Route Request 
- Added `suggestedSearchAtlas` in Poi Along Route Response  
- Added `actualGeoLevel` in Geocode Request
- Added `mapplsPinAdminType` in Geocode Response
- Remove `username`, `ignoreAutoExpand` & `includes` from Nearby Request
- Added `isVenue` & `venueDetails` in Reverse Geocode Response 
- Remove `username` from Text Search Request.
- Added `tollPass` and `eType` in Routing request. 
- Added `npToll` in Route Classes, `fromNodeIdx` and `toNodeIdx` in Leg Step & `consumption` in Annotation of Routing Response.  
- Fix request key `use_highways` from `use_highway` in Predective Route API
- Added Custom Style support

## v9.0.0, 11 August 2025
- Authentication and authorization mechanisms have been revised.
- Updated minimum Android version to 21.
- Added Support for 16 KB Page Sizes 