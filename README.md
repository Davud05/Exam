# CHECK LIST

- Create Postgres DB
- Input DB name in ./resources/config.properties
- When creating new entities, remember to add them to HibernateConfig

# Item Booking System API

## Why PUT for assigning items to students?

We use PUT instead of POST for assigning items to students because:

1. PUT is idempotent - multiple identical requests will have the same effect as a single request
2. We're updating an existing resource (the item) with a new state (assigned to a student)
3. The endpoint URL clearly identifies the resources being modified (/items/{itemId}/students/{studentId})
4. This follows REST principles where PUT is used for updating existing resources in a predictable way

POST would be more appropriate for creating new resources, while PUT is better for updates and establishing relationships between existing resources.


## api request responses

### Get all items
GET {{url}}/items
Accept: application/json
Authorization: Bearer {{jwt_token}}

GET http://localhost:7070/api/v1/items

HTTP/1.1 200 OK
Date: Thu, 21 Nov 2024 09:40:49 GMT
Content-Type: application/json
Content-Length: 1245

[
{
"id": 3,
"name": "New Camera",
"purchasePrice": 4999.99,
"category": "VIDEO",
"acquisitionDate": [
2024,
3,
20
],
"description": "High-end video camera",
"student": null
},
{
"id": 4,
"name": "Updated Camera",
"purchasePrice": 5999.99,
"category": "VIDEO",
"acquisitionDate": [
2024,
3,
20
],
"description": "Updated description",
"student": null
},
{
"id": 5,
"name": "New Camera",
"purchasePrice": 4999.99,
"category": "VIDEO",
"acquisitionDate": [
2024,
3,
20
],
"description": "High-end video camera",
"student": null
},
{
"id": 6,
"name": "Updated Camera",
"purchasePrice": 5999.99,
"category": "VIDEO",
"acquisitionDate": [
2024,
3,
20
],
"description": "Updated description",
"student": null
},
{
"id": 7,
"name": "New Camera",
"purchasePrice": 4999.99,
"category": "VIDEO",
"acquisitionDate": [
2024,
3,
20
],
"description": "High-end video camera",
"student": null
},
{
"id": 8,
"name": "Updated Camera",
"purchasePrice": 5999.99,
"category": "VIDEO",
"acquisitionDate": [
2024,
3,
20
],
"description": "Updated description",
"student": null
},
{
"id": 9,
"name": "Sony Camera",
"purchasePrice": 5999.99,
"category": "VIDEO",
"acquisitionDate": [
2024,
11,
21
],
"description": "Professional video camera",
"student": null
},
{
"id": 10,
"name": "VR Headset",
"purchasePrice": 3999.99,
"category": "VR",
"acquisitionDate": [
2024,
11,
21
],
"description": "Oculus Quest 2",
"student": null
}
]
Response file saved.
> 2024-11-21T104049.200.json

Response code: 200 (OK); Time: 57ms (57 ms); Content length: 1245 bytes (1,25 kB)

### Get item by id (including student details)
GET {{url}}/items/2
Accept: application/json
Authorization: Bearer {{jwt_token}}


GET http://localhost:7070/api/v1/items/2

HTTP/1.1 200 OK
Date: Thu, 21 Nov 2024 09:08:37 GMT
Content-Type: application/json
Content-Length: 246

{
"id": 2,
"name": "VR Headset",
"purchasePrice": 3999.99,
"category": "VR",
"acquisitionDate": [
2024,
11,
21
],
"description": "Oculus Quest 2",
"student": {
"id": 2,
"name": "Mickey Mouse",
"email": "mickey@mouse.dk",
"enrollmentDate": [
2024,
11,
21
],
"phone": "87654321"
}
}
Response file saved.
> 2024-11-21T100837.200.json

Response code: 200 (OK); Time: 66ms (66 ms); Content length: 246 bytes (246 B)

### Create new item
POST {{url}}/items
Content-Type: application/json
Authorization: Bearer {{jwt_token}}

POST http://localhost:7070/api/v1/items

HTTP/1.1 201 Created
Date: Thu, 21 Nov 2024 09:08:46 GMT
Content-Type: application/json
Content-Length: 154

{
"id": 5,
"name": "New Camera",
"purchasePrice": 4999.99,
"category": "VIDEO",
"acquisitionDate": [
2024,
3,
20
],
"description": "High-end video camera",
"student": null
}
Response file saved.
> 2024-11-21T100847.201.json

Response code: 201 (Created); Time: 515ms (515 ms); Content length: 154 bytes (154 B)


### Update item
PUT {{url}}/items/1
Content-Type: application/json
Authorization: Bearer {{jwt_token}}


PUT http://localhost:7070/api/v1/items/1

HTTP/1.1 200 OK
Date: Thu, 21 Nov 2024 09:08:53 GMT
Content-Type: application/json
Content-Length: 156

{
"id": 6,
"name": "Updated Camera",
"purchasePrice": 5999.99,
"category": "VIDEO",
"acquisitionDate": [
2024,
3,
20
],
"description": "Updated description",
"student": null
}
Response file saved.
> 2024-11-21T100853.200.json

Response code: 200 (OK); Time: 67ms (67 ms); Content length: 156 bytes (156 B)


### Delete item
DELETE {{url}}/items/1
Authorization: Bearer {{jwt_token}}

DELETE http://localhost:7070/api/v1/items/1

HTTP/1.1 204 No Content
Date: Thu, 21 Nov 2024 09:09:09 GMT
Content-Type: text/plain

<Response body is empty>

Response code: 204 (No Content); Time: 62ms (62 ms); Content length: 0 bytes (0 B)


### Assign item to student
PUT {{url}}/items/1/students/1
Authorization: Bearer {{jwt_token}}

PUT http://localhost:7070/api/v1/items/1/students/1

HTTP/1.1 204 No Content
Date: Thu, 21 Nov 2024 09:11:04 GMT
Content-Type: text/plain

<Response body is empty>;

Response code: 204 (No Content); Time: 39ms (39 ms); Content length: 0 bytes (0 B)


### Populate database
POST {{url}}/items/populate
Authorization: Bearer {{jwt_token}}


POST http://localhost:7070/api/v1/items/populate

HTTP/1.1 201 Created
Date: Thu, 21 Nov 2024 09:11:09 GMT
Content-Type: text/plain
Content-Length: 0

<Response body is empty>;

Response code: 201 (Created); Time: 15ms (15 ms); Content length: 0 bytes (0 B)


## Error handling

Changed my itemcontroller to have error handling on get item by id


    public static void getItemById(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        itemDAO.read(id)
                .map(ItemDTO::fromWithStudent)
                .ifPresentOrElse(
                        ctx::json,
                        () -> {
                            ctx.status(HttpStatus.NOT_FOUND);
                            ctx.json(Map.of(
                                    "message", "Item with ID: " + id + " not found",
                                    "path", ctx.path()
                            ));
                        }
                );
    }

#### So now the response when i try to get things out is 

GET http://localhost:7070/api/v1/items/999

HTTP/1.1 404 Not Found
Date: Thu, 21 Nov 2024 09:51:22 GMT
Content-Type: application/json
Content-Length: 68

{
"message": "Item with ID: 999 not found",
"path": "/api/v1/items/999"
}
Response file saved.
> 2024-11-21T105122.404.json

Response code: 404 (Not Found); Time: 389ms (389 ms); Content length: 68 bytes (68 B)

So now we get json to help with error handling.

### error handling on delete

    public static void deleteItem(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Optional<Item> item = itemDAO.read(id);

        if (item.isEmpty()) {
            ctx.status(HttpStatus.NOT_FOUND);
            ctx.json(Map.of(
                    "message", "Cannot delete - item with ID: " + id + " not found",
                    "path", ctx.path()
            ));
            return;
        }

        itemDAO.delete(id);
        ctx.status(HttpStatus.NO_CONTENT);
    }
#### so when i delete now and its an id that doesnt exist it will say this 

DELETE http://localhost:7070/api/v1/items/999

HTTP/1.1 404 Not Found
Date: Thu, 21 Nov 2024 09:58:23 GMT
Content-Type: application/json
Content-Length: 84

{
"message": "Cannot delete - item with ID: 999 not found",
"path": "/api/v1/items/999"
}
Response file saved.
> 2024-11-21T105823.404.json

Response code: 404 (Not Found); Time: 394ms (394 ms); Content length: 84 bytes (84 B)


### Task 5 

### Get items by category (I have duplicates, because it wont matter when you run it anyway i just need to finish)
GET {{url}}/items/category/VIDEO

GET http://localhost:7070/api/v1/items/category/VIDEO

HTTP/1.1 200 OK
Date: Thu, 21 Nov 2024 10:27:30 GMT
Content-Type: application/json
Content-Encoding: gzip
Content-Length: 248

[
{
"id": 3,
"name": "New Camera",
"purchasePrice": 4999.99,
"category": "VIDEO",
"acquisitionDate": [
2024,
3,
20
],
"description": "High-end video camera",
"student": null
},
{
"id": 4,
"name": "Updated Camera",
"purchasePrice": 5999.99,
"category": "VIDEO",
"acquisitionDate": [
2024,
3,
20
],
"description": "Updated description",
"student": null
},
{
"id": 5,
"name": "New Camera",
"purchasePrice": 4999.99,
"category": "VIDEO",
"acquisitionDate": [
2024,
3,
20
],
"description": "High-end video camera",
"student": null
},
{
"id": 6,
"name": "Updated Camera",
"purchasePrice": 5999.99,
"category": "VIDEO",
"acquisitionDate": [
2024,
3,
20
],
"description": "Updated description",
"student": null
},
{
"id": 7,
"name": "New Camera",
"purchasePrice": 4999.99,
"category": "VIDEO",
"acquisitionDate": [
2024,
3,
20
],
"description": "High-end video camera",
"student": null
},
{
"id": 8,
"name": "Updated Camera",
"purchasePrice": 5999.99,
"category": "VIDEO",
"acquisitionDate": [
2024,
3,
20
],
"description": "Updated description",
"student": null
},
{
"id": 9,
"name": "Sony Camera",
"purchasePrice": 5999.99,
"category": "VIDEO",
"acquisitionDate": [
2024,
11,
21
],
"description": "Professional video camera",
"student": null
},
{
"id": 11,
"name": "Sony Camera",
"purchasePrice": 5999.99,
"category": "VIDEO",
"acquisitionDate": [
2024,
11,
21
],
"description": "Professional video camera",
"student": null
},
{
"id": 13,
"name": "Sony Camera",
"purchasePrice": 5999.99,
"category": "VIDEO",
"acquisitionDate": [
2024,
11,
21
],
"description": "Professional video camera",
"student": null
},
{
"id": 15,
"name": "Sony Camera",
"purchasePrice": 5999.99,
"category": "VIDEO",
"acquisitionDate": [
2024,
11,
21
],
"description": "Professional video camera",
"student": null
},
{
"id": 17,
"name": "Sony Camera",
"purchasePrice": 5999.99,
"category": "VIDEO",
"acquisitionDate": [
2024,
11,
21
],
"description": "Professional video camera",
"student": null
},
{
"id": 19,
"name": "Sony Camera",
"purchasePrice": 5999.99,
"category": "VIDEO",
"acquisitionDate": [
2024,
11,
21
],
"description": "Professional video camera",
"student": null
},
{
"id": 21,
"name": "Sony Camera",
"purchasePrice": 5999.99,
"category": "VIDEO",
"acquisitionDate": [
2024,
11,
21
],
"description": "Professional video camera",
"student": null
}
]
Response file saved.
> 2024-11-21T112732.200.json

Response code: 200 (OK); Time: 2076ms (2 s 76 ms); Content length: 2070 bytes (2,07 kB)


### Get student item totals
GET {{url}}/items/student-totals

GET http://localhost:7070/api/v1/items/student-totals

HTTP/1.1 200 OK
Date: Thu, 21 Nov 2024 10:28:34 GMT
Content-Type: application/json
Content-Length: 638

[
{
"totalPurchasePrice": 5999.99,
"studentId": 3
},
{
"totalPurchasePrice": 3999.99,
"studentId": 4
},
{
"totalPurchasePrice": 5999.99,
"studentId": 5
},
{
"totalPurchasePrice": 3999.99,
"studentId": 6
},
{
"totalPurchasePrice": 5999.99,
"studentId": 7
},
{
"totalPurchasePrice": 3999.99,
"studentId": 8
},
{
"totalPurchasePrice": 5999.99,
"studentId": 9
},
{
"totalPurchasePrice": 3999.99,
"studentId": 10
},
{
"totalPurchasePrice": 5999.99,
"studentId": 11
},
{
"totalPurchasePrice": 3999.99,
"studentId": 12
},
{
"totalPurchasePrice": 5999.99,
"studentId": 13
},
{
"totalPurchasePrice": 3999.99,
"studentId": 14
},
{
"totalPurchasePrice": 5999.99,
"studentId": 15
},
{
"totalPurchasePrice": 3999.99,
"studentId": 16
}
]
Response file saved.
> 2024-11-21T112835.200.json

Response code: 200 (OK); Time: 295ms (295 ms); Content length: 638 bytes (638 B)


### Task 6 

### Get all shops
GET {{url}}/items/shops

GET http://localhost:7070/api/v1/items/shops

HTTP/1.1 200 OK
Date: Thu, 21 Nov 2024 10:28:57 GMT
Content-Type: application/json
Content-Encoding: gzip
Content-Length: 315

{
"shops": [
{
"id": 1,
"name": "Shop 1",
"url": "https://shop1.com",
"categories": [
"video",
"vr"
]
},
{
"id": 2,
"name": "Shop 2",
"url": "https://shop2.com",
"categories": [
"sound",
"print"
]
},
{
"id": 3,
"name": "Shop 3",
"url": "https://shop3.com",
"categories": [
"vr"
]
},
{
"id": 4,
"name": "Shop 4",
"url": "https://shop4.com",
"categories": [
"sound",
"tool"
]
},
{
"id": 5,
"name": "Shop 5",
"url": "https://shop5.com",
"categories": [
"sound"
]
},
{
"id": 6,
"name": "Shop 6",
"url": "https://shop6.com",
"categories": [
"video",
"tool"
]
},
{
"id": 7,
"name": "Shop 7",
"url": "https://shop7.com",
"categories": [
"vr",
"sound"
]
},
{
"id": 8,
"name": "Shop 8",
"url": "https://shop8.com",
"categories": [
"video"
]
},
{
"id": 9,
"name": "Shop 9",
"url": "https://shop9.com",
"categories": [
"tool"
]
},
{
"id": 10,
"name": "Shop 10",
"url": "https://shop10.com",
"categories": [
"print",
"vr"
]
},
{
"id": 11,
"name": "Shop 11",
"url": "https://shop11.com",
"categories": [
"video",
"sound"
]
},
{
"id": 12,
"name": "Shop 12",
"url": "https://shop12.com",
"categories": [
"sound",
"tool"
]
},
{
"id": 13,
"name": "Shop 13",
"url": "https://shop13.com",
"categories": [
"print"
]
},
{
"id": 14,
"name": "Shop 14",
"url": "https://shop14.com",
"categories": [
"video",
"vr"
]
},
{
"id": 15,
"name": "Shop 15",
"url": "https://shop15.com",
"categories": [
"sound"
]
},
{
"id": 16,
"name": "Shop 16",
"url": "https://shop16.com",
"categories": [
"print",
"tool"
]
},
{
"id": 17,
"name": "Shop 17",
"url": "https://shop17.com",
"categories": [
"vr"
]
},
{
"id": 18,
"name": "Shop 18",
"url": "https://shop18.com",
"categories": [
"sound",
"tool"
]
},
{
"id": 19,
"name": "Shop 19",
"url": "https://shop19.com",
"categories": [
"video",
"sound"
]
},
{
"id": 20,
"name": "Shop 20",
"url": "https://shop20.com",
"categories": [
"print",
"vr"
]
}
]
}
Response file saved.
> 2024-11-21T112901.200.json

Response code: 200 (OK); Time: 3859ms (3 s 859 ms); Content length: 1601 bytes (1,6 kB)

### Get shops by category
GET {{url}}/items/shops/video

GET http://localhost:7070/api/v1/items/shops/video

HTTP/1.1 200 OK
Date: Thu, 21 Nov 2024 10:37:13 GMT
Content-Type: application/json
Content-Length: 497

{
"shops": [
{
"id": 1,
"name": "Shop 1",
"url": "https://shop1.com",
"categories": [
"video",
"vr"
]
},
{
"id": 6,
"name": "Shop 6",
"url": "https://shop6.com",
"categories": [
"video",
"tool"
]
},
{
"id": 8,
"name": "Shop 8",
"url": "https://shop8.com",
"categories": [
"video"
]
},
{
"id": 11,
"name": "Shop 11",
"url": "https://shop11.com",
"categories": [
"video",
"sound"
]
},
{
"id": 14,
"name": "Shop 14",
"url": "https://shop14.com",
"categories": [
"video",
"vr"
]
},
{
"id": 19,
"name": "Shop 19",
"url": "https://shop19.com",
"categories": [
"video",
"sound"
]
}
]
}
Response file saved.
> 2024-11-21T113715.200.json

Response code: 200 (OK); Time: 2383ms (2 s 383 ms); Content length: 497 bytes (497 B)


### Get item with shops
GET http://localhost:7070/api/v1/items/3

GET http://localhost:7070/api/v1/items/3

HTTP/1.1 200 OK
Date: Thu, 21 Nov 2024 10:46:25 GMT
Content-Type: application/json
Content-Length: 658

{
"id": 3,
"name": "New Camera",
"purchasePrice": 4999.99,
"category": "VIDEO",
"acquisitionDate": [
2024,
3,
20
],
"description": "High-end video camera",
"student": null,
"relevantShops": [
{
"id": 1,
"name": "Shop 1",
"url": "https://shop1.com",
"categories": [
"video",
"vr"
]
},
{
"id": 6,
"name": "Shop 6",
"url": "https://shop6.com",
"categories": [
"video",
"tool"
]
},
{
"id": 8,
"name": "Shop 8",
"url": "https://shop8.com",
"categories": [
"video"
]
},
{
"id": 11,
"name": "Shop 11",
"url": "https://shop11.com",
"categories": [
"video",
"sound"
]
},
{
"id": 14,
"name": "Shop 14",
"url": "https://shop14.com",
"categories": [
"video",
"vr"
]
},
{
"id": 19,
"name": "Shop 19",
"url": "https://shop19.com",
"categories": [
"video",
"sound"
]
}
]
}
Response file saved.
> 2024-11-21T114625.200.json

Response code: 200 (OK); Time: 221ms (221 ms); Content length: 658 bytes (658 B)



### Task 7
The test uses a separate TestConfig class to create a clean test environment with its own Javalin instance. This separation ensures that tests don't interfere with the main application and can run independently.
Key components:
- TestConfig: Creates a dedicated test server configuration
- ItemRoutesTest: Contains the actual test cases
- @BeforeEach: Sets up test data before each test
- Assertions: Verifies both item details and shop data in the response

The test successfully verifies that:
1. Items can be retrieved by ID
2. Shop data is correctly included in the response
3. The shops match the item's category

The test passes, confirming that the endpoint integration with the shop API works as intended.

### Task 8 

No :) 


