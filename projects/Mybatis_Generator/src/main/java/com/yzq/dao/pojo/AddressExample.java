package com.yzq.dao.pojo;

import java.util.ArrayList;
import java.util.List;

public class AddressExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public AddressExample() {
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

        public Criteria andAddressIsNull() {
            addCriterion("address is null");
            return (Criteria) this;
        }

        public Criteria andAddressIsNotNull() {
            addCriterion("address is not null");
            return (Criteria) this;
        }

        public Criteria andAddressEqualTo(String value) {
            addCriterion("address =", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotEqualTo(String value) {
            addCriterion("address <>", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressGreaterThan(String value) {
            addCriterion("address >", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressGreaterThanOrEqualTo(String value) {
            addCriterion("address >=", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLessThan(String value) {
            addCriterion("address <", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLessThanOrEqualTo(String value) {
            addCriterion("address <=", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLike(String value) {
            addCriterion("address like", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotLike(String value) {
            addCriterion("address not like", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressIn(List<String> values) {
            addCriterion("address in", values, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotIn(List<String> values) {
            addCriterion("address not in", values, "address");
            return (Criteria) this;
        }

        public Criteria andAddressBetween(String value1, String value2) {
            addCriterion("address between", value1, value2, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotBetween(String value1, String value2) {
            addCriterion("address not between", value1, value2, "address");
            return (Criteria) this;
        }

        public Criteria andRecipientIsNull() {
            addCriterion("recipient is null");
            return (Criteria) this;
        }

        public Criteria andRecipientIsNotNull() {
            addCriterion("recipient is not null");
            return (Criteria) this;
        }

        public Criteria andRecipientEqualTo(String value) {
            addCriterion("recipient =", value, "recipient");
            return (Criteria) this;
        }

        public Criteria andRecipientNotEqualTo(String value) {
            addCriterion("recipient <>", value, "recipient");
            return (Criteria) this;
        }

        public Criteria andRecipientGreaterThan(String value) {
            addCriterion("recipient >", value, "recipient");
            return (Criteria) this;
        }

        public Criteria andRecipientGreaterThanOrEqualTo(String value) {
            addCriterion("recipient >=", value, "recipient");
            return (Criteria) this;
        }

        public Criteria andRecipientLessThan(String value) {
            addCriterion("recipient <", value, "recipient");
            return (Criteria) this;
        }

        public Criteria andRecipientLessThanOrEqualTo(String value) {
            addCriterion("recipient <=", value, "recipient");
            return (Criteria) this;
        }

        public Criteria andRecipientLike(String value) {
            addCriterion("recipient like", value, "recipient");
            return (Criteria) this;
        }

        public Criteria andRecipientNotLike(String value) {
            addCriterion("recipient not like", value, "recipient");
            return (Criteria) this;
        }

        public Criteria andRecipientIn(List<String> values) {
            addCriterion("recipient in", values, "recipient");
            return (Criteria) this;
        }

        public Criteria andRecipientNotIn(List<String> values) {
            addCriterion("recipient not in", values, "recipient");
            return (Criteria) this;
        }

        public Criteria andRecipientBetween(String value1, String value2) {
            addCriterion("recipient between", value1, value2, "recipient");
            return (Criteria) this;
        }

        public Criteria andRecipientNotBetween(String value1, String value2) {
            addCriterion("recipient not between", value1, value2, "recipient");
            return (Criteria) this;
        }

        public Criteria andRephoneIsNull() {
            addCriterion("rephone is null");
            return (Criteria) this;
        }

        public Criteria andRephoneIsNotNull() {
            addCriterion("rephone is not null");
            return (Criteria) this;
        }

        public Criteria andRephoneEqualTo(String value) {
            addCriterion("rephone =", value, "rephone");
            return (Criteria) this;
        }

        public Criteria andRephoneNotEqualTo(String value) {
            addCriterion("rephone <>", value, "rephone");
            return (Criteria) this;
        }

        public Criteria andRephoneGreaterThan(String value) {
            addCriterion("rephone >", value, "rephone");
            return (Criteria) this;
        }

        public Criteria andRephoneGreaterThanOrEqualTo(String value) {
            addCriterion("rephone >=", value, "rephone");
            return (Criteria) this;
        }

        public Criteria andRephoneLessThan(String value) {
            addCriterion("rephone <", value, "rephone");
            return (Criteria) this;
        }

        public Criteria andRephoneLessThanOrEqualTo(String value) {
            addCriterion("rephone <=", value, "rephone");
            return (Criteria) this;
        }

        public Criteria andRephoneLike(String value) {
            addCriterion("rephone like", value, "rephone");
            return (Criteria) this;
        }

        public Criteria andRephoneNotLike(String value) {
            addCriterion("rephone not like", value, "rephone");
            return (Criteria) this;
        }

        public Criteria andRephoneIn(List<String> values) {
            addCriterion("rephone in", values, "rephone");
            return (Criteria) this;
        }

        public Criteria andRephoneNotIn(List<String> values) {
            addCriterion("rephone not in", values, "rephone");
            return (Criteria) this;
        }

        public Criteria andRephoneBetween(String value1, String value2) {
            addCriterion("rephone between", value1, value2, "rephone");
            return (Criteria) this;
        }

        public Criteria andRephoneNotBetween(String value1, String value2) {
            addCriterion("rephone not between", value1, value2, "rephone");
            return (Criteria) this;
        }

        public Criteria andDeaddressIsNull() {
            addCriterion("deaddress is null");
            return (Criteria) this;
        }

        public Criteria andDeaddressIsNotNull() {
            addCriterion("deaddress is not null");
            return (Criteria) this;
        }

        public Criteria andDeaddressEqualTo(Boolean value) {
            addCriterion("deaddress =", value, "deaddress");
            return (Criteria) this;
        }

        public Criteria andDeaddressNotEqualTo(Boolean value) {
            addCriterion("deaddress <>", value, "deaddress");
            return (Criteria) this;
        }

        public Criteria andDeaddressGreaterThan(Boolean value) {
            addCriterion("deaddress >", value, "deaddress");
            return (Criteria) this;
        }

        public Criteria andDeaddressGreaterThanOrEqualTo(Boolean value) {
            addCriterion("deaddress >=", value, "deaddress");
            return (Criteria) this;
        }

        public Criteria andDeaddressLessThan(Boolean value) {
            addCriterion("deaddress <", value, "deaddress");
            return (Criteria) this;
        }

        public Criteria andDeaddressLessThanOrEqualTo(Boolean value) {
            addCriterion("deaddress <=", value, "deaddress");
            return (Criteria) this;
        }

        public Criteria andDeaddressIn(List<Boolean> values) {
            addCriterion("deaddress in", values, "deaddress");
            return (Criteria) this;
        }

        public Criteria andDeaddressNotIn(List<Boolean> values) {
            addCriterion("deaddress not in", values, "deaddress");
            return (Criteria) this;
        }

        public Criteria andDeaddressBetween(Boolean value1, Boolean value2) {
            addCriterion("deaddress between", value1, value2, "deaddress");
            return (Criteria) this;
        }

        public Criteria andDeaddressNotBetween(Boolean value1, Boolean value2) {
            addCriterion("deaddress not between", value1, value2, "deaddress");
            return (Criteria) this;
        }

        public Criteria andUidIsNull() {
            addCriterion("uid is null");
            return (Criteria) this;
        }

        public Criteria andUidIsNotNull() {
            addCriterion("uid is not null");
            return (Criteria) this;
        }

        public Criteria andUidEqualTo(Integer value) {
            addCriterion("uid =", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidNotEqualTo(Integer value) {
            addCriterion("uid <>", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidGreaterThan(Integer value) {
            addCriterion("uid >", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidGreaterThanOrEqualTo(Integer value) {
            addCriterion("uid >=", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidLessThan(Integer value) {
            addCriterion("uid <", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidLessThanOrEqualTo(Integer value) {
            addCriterion("uid <=", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidIn(List<Integer> values) {
            addCriterion("uid in", values, "uid");
            return (Criteria) this;
        }

        public Criteria andUidNotIn(List<Integer> values) {
            addCriterion("uid not in", values, "uid");
            return (Criteria) this;
        }

        public Criteria andUidBetween(Integer value1, Integer value2) {
            addCriterion("uid between", value1, value2, "uid");
            return (Criteria) this;
        }

        public Criteria andUidNotBetween(Integer value1, Integer value2) {
            addCriterion("uid not between", value1, value2, "uid");
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