package com.mongodb.reactive.reactivemongodb.payload

import com.mongodb.reactive.reactivemongodb.model.User

class UserResponse private constructor(resource: Resource<User>) : Response<User>(resource)