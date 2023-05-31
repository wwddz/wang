package com.yzq.dao.pojo;

import java.util.ArrayList;
import java.util.List;

public class RecommendedExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public RecommendedExample() {
        oredCriteria = new ArrayList<>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andComIsNull() {
            addCriterion("com is null");
            return (Criteria) this;
        }

        public Criteria andComIsNotNull() {
            addCriterion("com is not null");
            return (Criteria) this;
        }

        public Criteria andComEqualTo(Double value) {
            addCriterion("com =", value, "com");
            return (Criteria) this;
        }

        public Criteria andComNotEqualTo(Double value) {
            addCriterion("com <>", value, "com");
            return (Criteria) this;
        }

        public Criteria andComGreaterThan(Double value) {
            addCriterion("com >", value, "com");
            return (Criteria) this;
        }

        public Criteria andComGreaterThanOrEqualTo(Double value) {
            addCriterion("com >=", value, "com");
            return (Criteria) this;
        }

        public Criteria andComLessThan(Double value) {
            addCriterion("com <", value, "com");
            return (Criteria) this;
        }

        public Criteria andComLessThanOrEqualTo(Double value) {
            addCriterion("com <=", value, "com");
            return (Criteria) this;
        }

        public Criteria andComIn(List<Double> values) {
            addCriterion("com in", values, "com");
            return (Criteria) this;
        }

        public Criteria andComNotIn(List<Double> values) {
            addCriterion("com not in", values, "com");
            return (Criteria) this;
        }

        public Criteria andComBetween(Double value1, Double value2) {
            addCriterion("com between", value1, value2, "com");
            return (Criteria) this;
        }

        public Criteria andComNotBetween(Double value1, Double value2) {
            addCriterion("com not between", value1, value2, "com");
            return (Criteria) this;
        }

        public Criteria andCntIsNull() {
            addCriterion("cnt is null");
            return (Criteria) this;
        }

        public Criteria andCntIsNotNull() {
            addCriterion("cnt is not null");
            return (Criteria) this;
        }

        public Criteria andCntEqualTo(Integer value) {
            addCriterion("cnt =", value, "cnt");
            return (Criteria) this;
        }

        public Criteria andCntNotEqualTo(Integer value) {
            addCriterion("cnt <>", value, "cnt");
            return (Criteria) this;
        }

        public Criteria andCntGreaterThan(Integer value) {
            addCriterion("cnt >", value, "cnt");
            return (Criteria) this;
        }

        public Criteria andCntGreaterThanOrEqualTo(Integer value) {
            addCriterion("cnt >=", value, "cnt");
            return (Criteria) this;
        }

        public Criteria andCntLessThan(Integer value) {
            addCriterion("cnt <", value, "cnt");
            return (Criteria) this;
        }

        public Criteria andCntLessThanOrEqualTo(Integer value) {
            addCriterion("cnt <=", value, "cnt");
            return (Criteria) this;
        }

        public Criteria andCntIn(List<Integer> values) {
            addCriterion("cnt in", values, "cnt");
            return (Criteria) this;
        }

        public Criteria andCntNotIn(List<Integer> values) {
            addCriterion("cnt not in", values, "cnt");
            return (Criteria) this;
        }

        public Criteria andCntBetween(Integer value1, Integer value2) {
            addCriterion("cnt between", value1, value2, "cnt");
            return (Criteria) this;
        }

        public Criteria andCntNotBetween(Integer value1, Integer value2) {
            addCriterion("cnt not between", value1, value2, "cnt");
            return (Criteria) this;
        }

        public Criteria andShopidIsNull() {
            addCriterion("shopid is null");
            return (Criteria) this;
        }

        public Criteria andShopidIsNotNull() {
            addCriterion("shopid is not null");
            return (Criteria) this;
        }

        public Criteria andShopidEqualTo(Integer value) {
            addCriterion("shopid =", value, "shopid");
            return (Criteria) this;
        }

        public Criteria andShopidNotEqualTo(Integer value) {
            addCriterion("shopid <>", value, "shopid");
            return (Criteria) this;
        }

        public Criteria andShopidGreaterThan(Integer value) {
            addCriterion("shopid >", value, "shopid");
            return (Criteria) this;
        }

        public Criteria andShopidGreaterThanOrEqualTo(Integer value) {
            addCriterion("shopid >=", value, "shopid");
            return (Criteria) this;
        }

        public Criteria andShopidLessThan(Integer value) {
            addCriterion("shopid <", value, "shopid");
            return (Criteria) this;
        }

        public Criteria andShopidLessThanOrEqualTo(Integer value) {
            addCriterion("shopid <=", value, "shopid");
            return (Criteria) this;
        }

        public Criteria andShopidIn(List<Integer> values) {
            addCriterion("shopid in", values, "shopid");
            return (Criteria) this;
        }

        public Criteria andShopidNotIn(List<Integer> values) {
            addCriterion("shopid not in", values, "shopid");
            return (Criteria) this;
        }

        public Criteria andShopidBetween(Integer value1, Integer value2) {
            addCriterion("shopid between", value1, value2, "shopid");
            return (Criteria) this;
        }

        public Criteria andShopidNotBetween(Integer value1, Integer value2) {
            addCriterion("shopid not between", value1, value2, "shopid");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}