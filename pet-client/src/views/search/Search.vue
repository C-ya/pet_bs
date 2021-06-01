<template>
  <div class="container">
    <Row>
      <iCol>
        <sui-message>
          文章搜索结果：共检索到 {{searchResult.totalElements}} 条记录
        </sui-message>
        <sui-card class="fluid">
          <sui-card-content>
            <sui-feed class="article-feed">
              <sui-feed-event style="margin-bottom: 20px" v-for="article in searchResult.content" :key="article.id">
                <sui-feed-content>
                  <sui-feed-summary>
                    <router-link :to="'/article/' + article.id">
                      <span class="article-title" v-html="article.title"/>
                    </router-link>
                    <sui-feed-date>
                      <Time :time="article.createTime"/>
                    </sui-feed-date>
                  </sui-feed-summary>
                  <sui-feed-extra>
                    <Tag color="blue">{{article.articleCategory}}</Tag>
                    <Tag v-for="(tag, index) in article.tagList" :key="index">{{tag}}</Tag>
                  </sui-feed-extra>
                  <sui-feed-extra class="article-brief">
                    <p v-html="article.brief"/>
                  </sui-feed-extra>
                </sui-feed-content>
              </sui-feed-event>
            </sui-feed>
          </sui-card-content>
        </sui-card>
      </iCol>
    </Row>
  </div>
</template>

<script>
export default {
  name: 'Search',
  props: ['q'],
  data() {
    return {
      param: {
        page: 0,
        size: 20
      },
      searchResult: {
        'content': [
          {
            'id': 5,
            'userId': 3,
            'resourceId': 8,
            'articleCategory': '社区新闻',
            'title': '2018/2019社区宠物爱护嘉奖',
            'brief': '2018/2019社区宠物爱护嘉奖',
            'cover': 'http://img.angus-liu.cn/avatar/avatar01.jpg',
            'tags': '["知乎热门", "专业在线"]',
            'tagList': [
              '中北奖章',
              '校长奖章'
            ],
            'markdownContent': '2018年10月30日下午2:30，  ',
            'htmlContent': '<p>2018年10月30日下午2:30，',
            'createTime': '2019-05-15',
            'updateTime': '2019-05-26T15:19:27.000+0000'
          }
        ],
        'totalElements': 5,
        'totalPages': 5,
        'number': 0,
        'size': 1,
      }
    };
  },
  methods: {
    search() {
      this.param.q = this.q;
      this.$axios.get('/api/article/search', this.param)
        .then(res => {
          let result = res.data;
          this.searchResult = result.data;
        });
    }
  },
  watch: {
    q() {
      this.search();
    }
  },
  mounted() {
    this.search();
  }
}
</script>

<style scoped>
.container {
  width: 1140px;
  margin: 0 auto;
  padding: 5px;

}

.ivu-col {
  padding: 7px;
}

.article-title >>> em, .article-brief >>> em {
  font-style: normal;
  color: #ff8364;
}


</style>
