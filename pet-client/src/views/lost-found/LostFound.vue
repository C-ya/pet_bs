<template>
  <div class="container">
    <Row>
      <iCol class="column" span="16">
        <!-- 失宠寻宠菜单 -->
        <sui-menu>
          <sui-menu-item
            link
            v-for="item in menu.items"
            :key="item"
            :content="item"
            :active="item === menu.active"
            @click="select(item)"
          />
          <!-- <sui-menu-item position="right">
            <sui-input transparent icon="search" placeholder="搜索"
                       v-model="param.general" @keydown.enter="getLostFoundPage(param)"/>
          </sui-menu-item> -->
        </sui-menu>
        <!-- 失宠寻宠菜单 END -->
        <!-- 失宠寻宠列表 -->
        <sui-card
          class="fluid lost-found-card"
          v-for="lostFound in lostFoundPage.records"
          :key="lostFound.id"
        >
          <sui-card-content>
            <sui-feed>
              <sui-feed-event>
                <sui-feed-content>
                  <sui-feed-summary>
                    <sui-label
                      class="lost-found-category"
                      basic
                      :color="
                        lostFound.lostFoundCategory === '寻宠启事'
                          ? 'red'
                          : 'green'
                      "
                    >
                      {{ lostFound.lostFoundCategory }}
                    </sui-label>
                    <router-link
                      class="lost-found-title"
                      :to="'/lost-found/' + lostFound.id"
                      >{{ lostFound.title }}
                    </router-link>
                    <sui-feed-date>
                      <Time :time="lostFound.createTime" />
                    </sui-feed-date>
                  </sui-feed-summary>
                  <sui-feed-extra class="lost-found-extra" text>
                    <Row>
                      <iCol span="7">👜 {{ lostFound.itemName }}</iCol>
                      <iCol span="9">🕓 {{ lostFound.time }}</iCol>
                      <iCol span="5" offset="3"
                        >📍 {{ lostFound.address }}</iCol
                      >
                    </Row>
                    <div class="lost-found-description">
                      {{ lostFound.description }}
                    </div>
                  </sui-feed-extra>
                  <sui-feed-extra
                    class="lost-found-images"
                    images
                    v-if="lostFound.images && lostFound.images.length > 0"
                  >
                    <img v-for="img in lostFound.images" :src="img" />
                  </sui-feed-extra>
                  <sui-feed-meta>
                    <router-link :to="'/user/' + lostFound.user.id">
                      <sui-image
                        :src="lostFound.user.avatar"
                        size="medium"
                        avatar
                      />
                      <span style="position: relative; top: 2px">{{
                        lostFound.user.nickname
                      }}</span>
                    </router-link>
                    <sui-feed-like @click="addZan(lostFound.resource)">
                      👍 {{ lostFound.resource.zanCount }}
                    </sui-feed-like>
                    <sui-feed-like @click="getCommentList(lostFound.resource)">
                      💬 {{ lostFound.resource.commentCount }}
                    </sui-feed-like>
                  </sui-feed-meta>
                </sui-feed-content>
              </sui-feed-event>
            </sui-feed>
          </sui-card-content>
        </sui-card>
        <!-- 失宠寻宠列表 END -->
        <!-- 加载更多 -->
        <sui-button
          class="fluid"
          basic
          content="加载更多"
          @click="loadMore"
          :disabled="param.current >= lostFoundPage.pages"
        />
        <!-- 加载更多 END -->
      </iCol>
      <iCol class="column" span="8">
        <!-- 寻宠启事提示 -->
        <sui-card class="fluid">
          <sui-message attached="top" icon="eye" negative>
            <sui-message-header>寻宠启事小贴士</sui-message-header>
            <p>
              什么，宠物走丢啦？不要怕，快来发布一条寻宠启事吧，很快就会有好心人联系你啦。对啦，一定要核实信息，准确后再发布哦
              😃
            </p>
          </sui-message>
          <sui-button
            attached="bottom"
            icon="add"
            content="发布寻宠启事"
            @click="showLostFoundModal('寻宠启事')"
          />
        </sui-card>
        <!-- 寻宠启事提示 END -->
        <!-- 失宠招领提示 -->
        <sui-card class="fluid">
          <sui-message attached="top" icon="bullhorn" positive>
            <sui-message-content>
              <sui-message-header>失宠招领小贴士</sui-message-header>
              <p>
                发现一只疑似走丢的小可爱？快发布一条失宠招领吧，失主一定会非常非常感激你呢
                😘
              </p>
            </sui-message-content>
          </sui-message>
          <sui-button
            attached="bottom"
            icon="add"
            content="发布失宠招领"
            @click="showLostFoundModal('失宠招领')"
          />
        </sui-card>
        <!-- 失宠招领提示 END -->
      </iCol>
    </Row>
    <!-- 失宠寻宠添加模态框 -->
    <Modal
      v-model="lostFoundAddModal.show"
      :title="lostFoundAddModal.title"
      width="600"
      scrollable
    >
      <Form :model="lostFoundAddModal.form" :label-width="50">
        <FormItem label="标题" required>
          <Input
            v-model="lostFoundAddModal.form.title"
            size="large"
            placeholder="清晰的标题能让更多人注意到"
          />
        </FormItem>
        <FormItem label="昵称" required>
          <Input
            v-model="lostFoundAddModal.form.itemName"
            size="large"
            placeholder="请填写宠物昵称"
          />
        </FormItem>
        <FormItem label="时间" required>
          <DatePicker
            :value="lostFoundAddModal.form.time"
            @on-change="(time) => (lostFoundAddModal.form.time = time)"
            type="datetime"
            size="large"
            :placeholder="lostFoundAddModal.placeholder.time"
            style="width: 100%"
          />
        </FormItem>
        <FormItem label="地点" required>
          <Input
            v-model="lostFoundAddModal.form.address"
            size="large"
            :placeholder="lostFoundAddModal.placeholder.address"
          />
        </FormItem>
        <FormItem label="描述" required>
          <Input
            v-model="lostFoundAddModal.form.description"
            type="textarea"
            size="large"
            :autosize="{ minRows: 5, maxRows: 10 }"
            placeholder="输入宠物描述，有助于他人获取信息"
          />
        </FormItem>
        <FormItem label="图片" style="margin-bottom: 0">
          <ImageUploader
            ref="imageUploader"
            @images-change="
              (images) => (lostFoundAddModal.form.images = images)
            "
          />
        </FormItem>
      </Form>
      <template #footer>
        <Button type="text" @click="lostFoundAddModal.show = false"
          >取消</Button
        >
        <Button @click="addLostFound">确定发布</Button>
      </template>
    </Modal>
    <!-- 失宠寻宠添加模态框 END -->
    <!-- 评论模态框 -->
    <Modal
      v-model="commentAddModal.show"
      title="发表评论"
      width="600"
      footer-hide
    >
      <Input
        ref="commentInput"
        v-model="commentAddModal.form.value"
        type="textarea"
        :rows="3"
        placeholder="添加评论"
        @on-enter="addComment"
      />
      <sui-comment-group class="comment-group">
        <sui-comment v-for="comment in commentList" :key="comment.id">
          <sui-comment-avatar :src="comment.postUser.avatar" />
          <sui-comment-content>
            <a is="sui-comment-author">{{ comment.postUser.nickname }}</a>
            <sui-comment-metadata>
              <Time :time="comment.createTime" />
            </sui-comment-metadata>
            <sui-comment-text>{{ comment.content }}</sui-comment-text>
            <sui-comment-actions>
              <sui-comment-action
                @click="replyComment(comment.id, comment.postUser)"
                >回复</sui-comment-action
              >
            </sui-comment-actions>
          </sui-comment-content>
          <!-- 子评论 -->
          <sui-comment-group v-if="comment.children.length > 0">
            <sui-comment v-for="child in comment.children" :key="child.id">
              <sui-comment-avatar :src="child.postUser.avatar" />
              <sui-comment-content>
                <a is="sui-comment-author">{{ child.postUser.nickname }}</a>
                <sui-comment-metadata>
                  <Time :time="child.createTime" />
                </sui-comment-metadata>
                <sui-comment-text>
                  <a :href="child.replyUser.id"
                    >@{{ child.replyUser.nickname }}</a
                  >
                  {{ child.content }}
                </sui-comment-text>
                <sui-comment-actions>
                  <sui-comment-action
                    @click="replyComment(comment.id, child.postUser)"
                    >回复</sui-comment-action
                  >
                </sui-comment-actions>
              </sui-comment-content>
            </sui-comment>
          </sui-comment-group>
          <!-- 子评论 END -->
        </sui-comment>
      </sui-comment-group>
    </Modal>
    <!-- 评论模态框 END -->
  </div>
</template>

<script>
import ImageUploader from "@/components/common/ImageUploader";

export default {
  name: "LostFound",
  components: {
    ImageUploader,
  },
  data() {
    return {
      menu: {
        active: "查看所有",
        items: ["查看所有", "失宠招领", "寻宠启事"],
      },
      param: {
        // query 参数
        lostFoundCategory: null,
        general: null,
        // page & order 参数
        desc: "create_time",
        current: 1,
      },
      lostFoundPage: {
        records: [
          {
            id: 1,
            userId: 1,
            resourceId: 16,
            lostFoundCategory: "寻宠启事",
            title: "四栋丢失一只狸花猫",
            description: "如图，四个月猫咪，亲人，胆小",
            itemName: "妮妮",
            time: "2019-05-17 12:10:01",
            address: "四栋",
            createTime: "2019-05-17 15:39:45",
            images: ["../../assets/img/pet/10.jpg"],
            user: {
              id: 1,
              username: "admin",
              nickname: "管理员",
              avatar: "http://img.angus-liu.cn/avatar/avatar07.png",
              sign: "一句话介绍自己",
            },
            resource: {
              id: 16,
              zanCount: 0,
              pvCount: 0,
              commentCount: 0,
            },
          },
        ],
        total: 5,
        size: 1,
        current: 1,
        searchCount: true,
        pages: 5,
      },
      lostFoundAddModal: {
        show: false,
        title: "发布寻宠启事",
        placeholder: {
          time: "请选择丢失时间",
          address: "请填写丢失地点",
        },
        form: {
          lostFoundCategory: "寻宠启事",
          title: "",
          itemName: "",
          time: null,
          address: "",
          description: "",
          images: null,
        },
      },
      commentList: [
        {
          id: -1,
          content: "测试评论",
          createTime: "2019-05-21 20:15:39",
          postUser: {
            id: -1,
            username: "admin",
            nickname: "管理员",
            avatar: "http://img.angus-liu.cn/avatar/avatar07.png",
          },
          children: [
            {
              id: -2,
              content: "测试回复",
              createTime: "2019-05-21 20:15:46",
              postUser: {
                id: 1,
                username: "admin",
                nickname: "管理员",
                avatar: "http://img.angus-liu.cn/avatar/avatar07.png",
              },
              replyUser: {
                id: 1,
                username: "admin",
                nickname: "管理员",
                avatar: "http://img.angus-liu.cn/avatar/avatar07.png",
              },
            },
          ],
        },
      ],
      commentAddModal: {
        show: false,
        form: {
          parentId: null,
          resourceId: null,
          replyUserId: null,
          value: "",
          content: "",
        },
      },
      currentResource: {},
    };
  },
  methods: {
    // 切换菜单
    select(item) {
      this.menu.active = item;
      switch (item) {
        case "查看所有":
          this.param.lostFoundCategory = null;
          break;
        case "失宠招领":
          this.param.lostFoundCategory = "失宠招领";
          break;
        case "寻宠启事":
          this.param.lostFoundCategory = "寻宠启事";
          break;
      }
      this.param.current = 1;
      this.getLostFoundPage(this.param);
    },
    // 获取失宠寻宠分页
    getLostFoundPage(param) {
      this.$axios.get("/api/lost-found/page", param).then((res) => {
        let result = res.data;
        this.lostFoundPage = result.data;
        this.lostFoundPage.records.forEach((lostFound) => {
          if (lostFound.images && lostFound.images.length > 0) {
            lostFound.images = JSON.parse(lostFound.images);
          }
        });
      });
    },
    // 配置失宠寻宠添加模态框
    showLostFoundModal(type) {
      if (type === "寻宠启事") {
        this.lostFoundAddModal = {
          show: true,
          title: "发布寻宠启事",
          placeholder: {
            time: "请选择丢失时间",
            address: "请填写丢失地点",
          },
          form: {
            lostFoundCategory: "寻宠启事",
            title: "",
            itemName: "",
            time: null,
            address: "",
            description: "",
            images: null,
          },
        };
      } else {
        this.lostFoundAddModal = {
          show: true,
          title: "发布失宠招领",
          placeholder: {
            time: "请选择发现时间",
            address: "请填写发现地点",
          },
          form: {
            lostFoundCategory: "失宠招领",
            title: "",
            itemName: "",
            time: null,
            address: "",
            description: "",
            images: null,
          },
        };
      }
    },
    // 添加失宠寻宠
    addLostFound() {
      let lostFoundAddForm = this.lostFoundAddModal.form;
      lostFoundAddForm.images = JSON.stringify(lostFoundAddForm.images);
      this.$axios.post("/api/lost-found", lostFoundAddForm).then((res) => {
        let result = res.data;
        if (result.success) {
          this.$Notice.success({ title: "Bingo", desc: "发布成功" });
          this.lostFoundAddModal.show = false;
          this.$refs.imageUploader.clearImages();
          this.getLostFoundPage(this.param);
        }
      });
    },
    // 加载更多
    loadMore() {
      this.param.current++;
      this.$axios.get("/api/lost-found/page", this.param).then((res) => {
        let result = res.data;
        let moreLostFoundPage = result.data;
        moreLostFoundPage.records.forEach((lostFound) => {
          if (lostFound.images && lostFound.images.length > 0) {
            lostFound.images = JSON.parse(lostFound.images);
          }
        });
        this.lostFoundPage.records.push(...moreLostFoundPage.records);
      });
    },
    // 点赞
    addZan(resource) {
      this.$axios
        .post("/api/zan", { resourceId: resource.id })
        .then((res) => resource.zanCount++);
    },
    // 获取评论
    getCommentList(resource) {
      this.currentResource = resource;
      this.$axios.get(`/api/comment/${resource.id}`).then((res) => {
        let result = res.data;
        this.commentList = result.data;
        // 展示评论
        this.commentAddModal.show = true;
      });
    },
    // 初始化评论
    initComment() {
      this.commentAddModal.form = {
        resourceId: null,
        parentId: null,
        replyUserId: null,
        value: "",
        content: "",
      };
    },
    // 添加评论
    addComment() {
      // 设置资源 ID
      this.commentAddModal.form.resourceId = this.currentResource.id;
      // 判断是评论还是回复
      if (
        this.commentAddModal.form.value.startsWith("@") &&
        this.commentAddModal.form.replyUserId !== null
      ) {
        // 回复时去掉评论内容中的回复用户名
        let index = this.commentAddModal.form.value.indexOf(" ");
        this.commentAddModal.form.content = this.commentAddModal.form.value.substr(
          index + 1
        );
      } else {
        this.commentAddModal.form.parentId = null;
        this.commentAddModal.form.replyUserId = null;
        this.commentAddModal.form.content = this.commentAddModal.form.value;
      }
      this.$axios
        .post("/api/comment", this.commentAddModal.form)
        .then((res) => {
          this.initComment();
          this.currentResource.commentCount++;
          this.getCommentList(this.currentResource);
        });
    },
    // 回复评论
    replyComment(parentId, replyUser) {
      this.commentAddModal.form.parentId = parentId;
      this.commentAddModal.form.replyUserId = replyUser.id;
      this.commentAddModal.form.value = `@${replyUser.nickname} `;
      this.$refs.commentInput.focus();
    },
  },
  mounted() {
    this.getLostFoundPage(this.param);
  },
};
</script>

<style lang="less" scoped>
.container {
  //width: 1140px;
  width: 80%;
  margin: 0 auto;
  padding: 5px;

  .column {
    padding: 7px;
  }

  .lost-found-card {
    .lost-found-category {
      margin-right: 5px;
    }

    a.lost-found-title {
      color: #455a64;
    }

    a.lost-found-title:hover {
      color: #ff8364;
    }

    .lost-found-extra {
      width: 100%;

      .lost-found-description {
        margin: 10px 0;
        color: #444;
      }
    }
  }
}
</style>
