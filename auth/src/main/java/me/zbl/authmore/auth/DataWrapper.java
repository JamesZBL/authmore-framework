/*
 * Copyright 2019 ZHENG BAO LE
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package me.zbl.authmore.auth;

import java.util.List;

/**
 * @author ZHENG BAO LE
 * @since 2019-02-10
 */
public class DataWrapper<T> {

    private static int DEFAULT_PAGE_SIZE = 20;
    private static int DEFAULT_CURRENT_PAGE = 1;

    static class PaginationWrapper {

        private int total = 999;
        private int currentPage = 1;
        private int pageSize = 20;

        public PaginationWrapper(int total, int currentPage, int pageSize) {
            this.total = total;
            this.currentPage = currentPage;
            this.pageSize = pageSize;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }
    }

    private List<T> list;
    private PaginationWrapper pagination;

    public DataWrapper(List<T> list, PaginationWrapper pagination) {
        this.list = list;
        this.pagination = pagination;
    }

    public DataWrapper(List<T> list) {
        this(list, new PaginationWrapper(list.size(), DEFAULT_CURRENT_PAGE, DEFAULT_PAGE_SIZE));
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public PaginationWrapper getPagination() {
        return pagination;
    }

    public void setPagination(PaginationWrapper pagination) {
        this.pagination = pagination;
    }
}
