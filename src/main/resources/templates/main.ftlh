<#import "parts/common.ftlh" as c>

<@c.page>
    <div class="container text-center">
    <h1 style="color: white; padding-top: 45px; ">ПРИКЛЮЧЕНИЯ НАЧИНАЮТСЯ ЗДЕСЬ... 😊✌️✈️</h1>
    </div>



    <div class="container-fluid d-flex h-100 justify-content-center align-items-center p-0 pt-5">

        <div class="row shadow p-3 mb-2 bg-white rounded " style="width: 900px">
            <form action="/search" method="get">
            <div class="row g-3 pb-2">

                <div class="form-inline  ml-4" >
                    <input type="text" class="form-control" placeholder="Откуда" style="width: 300px;" name="departure">
                </div>
                <img src="data:image/png;base64,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" width="60px" height="60px" alt="arrow-right.png" border="0">
                <div class="form-inline mr-5">
                    <input type="text" class="form-control" placeholder="Куда" style="width: 300px;" name="destination">
                </div>
                <input type="hidden" name="_csrf" value="${_csrf.token}">
                <div class="form-inline mr-2">
                    <button type="submit" style="width: 150px" class="btn btn-primary">ПОИСК</button>
                </div>

            </div>
            </form>
            <form action="/search/all" method="get">
                <div>
                    <input type="hidden" name="_csrf" value="${_csrf.token}">
                <button type="submit" style="background: none;border: none;color: blue;float:right;cursor: pointer; text-decoration: underline;ffont-size: 8px; font-family: inherit;" class="btn btn-primary">все перелеты</button>
                </div>
            </form>
        </div>
    </div>


</@c.page>