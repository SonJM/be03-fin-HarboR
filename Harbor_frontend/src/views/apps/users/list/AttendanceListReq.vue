<script setup lang="ts">
import { ref, onMounted, computed } from 'vue';
import 'vue3-easy-data-table/dist/style.css';
import UiParentCard from '@/components/shared/UiParentCard.vue';
import { useCodeStore } from '@/stores/codetrans';
import axios, { setClientHeaders } from '@/utils/axios';
import AttendanceListDetail from '@/views/apps/users/list/AttendanceListDetail.vue';
const codeStore = useCodeStore();
const list = ref<any[]>([]);
const baseUrl = `${import.meta.env.VITE_API_URL}`;
onMounted(() => {
  fetchStatus();
});
const getStatusCode = (payStatusCode) => {
  return codeStore.getStatusNameByCode(payStatusCode);
};
async function fetchStatus() {
  try {
    setClientHeaders();
    const response = await axios.get(`${baseUrl}/total/annual/read/send`);
    const tempItems = response.data.result;
    console.log("결과물" + tempItems);
    console.log("아이디" + tempItems.annualId);
    // 객체의 모든 속성을 문자열로 출력
console.log("결과물1", JSON.stringify(tempItems));
    // 모든 결재자의 이름을 조회합니다.
    const updatedItems = await Promise.all(
      tempItems.map(async (item) => {
        const firstApprovalName = await employeeIdByName(item.firstApprovalId);
        const secondApprovalName = await employeeIdByName(item.secondApprovalId);
        const thirdApprovalName = await employeeIdByName(item.thirdApprovalId);
        const status = calculateStatus(item);
        console.log(status);
        console.log("1"+firstApprovalName);
        console.log("2"+secondApprovalName);
        console.log("3"+thirdApprovalName);
        return {
          ...item,
          firstApprovalName: firstApprovalName || 'N/A',
          secondApprovalName: secondApprovalName || 'N/A',
          thirdApprovalName: thirdApprovalName || 'N/A',
          status: status || 'N/A',
        };
      })
    );
    list.value = updatedItems;
    console.log("test" + list.value);
  } catch (error) {
    alert('결재 목록 조회에 실패하였습니다.');
    console.log(error);
  }
}
type ListItem = {
  payStatusCode: string;
  firstApprovalId: string;
  secondApprovalId: string;
  thirdApprovalId: string;
  firstApprovalDate: string;
  secondApprovalDate: string;
  thirdApprovalDate: string;
  annualId: number;
  firstApprovalName: string;
  secondApprovalName: string;
  thirdApprovalName: string;
  status:string;
};
const headers: Header[] = [
  { text: '결재 종류', value: 'payStatusCode', sortable: true },
  { text: '1차 결재자', value: 'firstApprovalId', sortable: true },
  { text: '2차 결재자', value: 'secondApprovalId', sortable: true },
  { text: '3차 결재자', value: 'thirdApprovalId', sortable: true },
  { text: '진행 상태', value: 'status', sortable: true }
];
const listCards = computed<ListItem[]>(() => {
  return list.value;
});
const details = ref(false);
const selectAttendance = ref<ListItem | null>(null);
const items = ref(listCards);
const showRow = (item: ListItem) => {
  details.value = true;
  selectAttendance.value = item;
};

// 사원 번호로 이름 변경
async function employeeIdByName(employeeId) {
  try {
    setClientHeaders();
    const response = await axios.get(`${baseUrl}/total/employee/name/${employeeId}`);
    return response.data.result;
  } catch (error) {
    alert('결재 목록 조회에 실패하였습니다.');
    console.log(error);
  }
};
// 상태 계산 함수
function calculateStatus(item: ListItem): string {
  if (item.firstApprovalDate && item.secondApprovalDate && item.thirdApprovalDate) {
    return '완료';
  } else if (item.firstApprovalDate === 'companion' || item.secondApprovalDate === 'companion' || item.thirdApprovalDate === 'companion') {
    return '완료';
  } else {
    return '진행중';
  }
}

</script>
<template>
  <v-row>
    <v-col cols="12" md="12">
      <UiParentCard title="전자 결재">
        <v-divider></v-divider>
        <!-- 진행중일경우 -->
        <div class="overflow-auto mt-2">
          <EasyDataTable
            @click-row="showRow"
            :headers="headers"
            :items="items"
            table-class-name="customize-table action-position"
            :rows-per-page="8"
            v-if="!details"
          >
            <!-- 휴가 종류 -->
            <template #item-payStatusCode="{ payStatusCode}">
              <div class="d-flex align-center ga-4">
                <div>
                  <h5 class="text-h5">
                    {{ getStatusCode(payStatusCode) }}
                  </h5>
                </div>
              </div>
            </template>
            <!-- 1차 승인자 -->
            <template #item-firstApprovalId="{ firstApprovalName, firstApprovalDate}">
              <div class="d-flex align-center ga-4">
                <div>
                  <h5 class="text-h5" >
                    {{ firstApprovalName }}
                  </h5>
                  <small v-if="firstApprovalDate" class="text-subtitle text-center" style="color: green">{{ firstApprovalDate }} </small>
                  <small v-if="!firstApprovalDate" class="text-subtitle text-center" style="color: blue"> 진행중 </small>
                </div>
              </div>
            </template>
            <template #item-secondApprovalId="{ secondApprovalName, secondApprovalDate, firstApprovalDate}">
              <div class="d-flex align-center ga-4">
                <div>
                  <h5 class="text-h5">
                    {{ secondApprovalName }}
                  </h5>
                  <small v-if="secondApprovalDate" class="text-subtitle text-center">{{ secondApprovalDate }} </small>
                  <small v-if="firstApprovalDate && !secondApprovalDate" class="text-subtitle text-center" style="color: blue">
                    진행중
                  </small>
                  <small v-if="!firstApprovalDate && !secondApprovalDate" class="text-subtitle text-center"> </small>
                </div>
              </div>
            </template>
            <template #item-thirdApprovalId="{ thirdApprovalName, thirdApprovalDate, secondApprovalDate}">
              <div class="d-flex align-center ga-4">
                <div>
                  <h5 class="text-h5">
                    {{ thirdApprovalName }}
                  </h5>
                  <small v-if="thirdApprovalDate" class="text-subtitle text-center">{{ thirdApprovalDate }} </small>
                  <small v-if="!thirdApprovalDate && secondApprovalDate" class="text-subtitle text-center" style="color: blue">
                    진행중
                  </small>
                  <small v-if="!thirdApprovalDate && !secondApprovalDate" class="text-subtitle text-center"> </small>
                </div>
              </div>
            </template>
            <template #item-status="{ status }">
              <div class="d-flex align-center ga-4">
                <div>
                  {{ status }}
                </div>
              </div>
            </template>
          </EasyDataTable>
        </div>
        <!-- 상세보기 -->
        <div v-if="details && selectAttendance" class="pa-5">
          <AttendanceListDetail @toggleDetail="details = false" :selectedDetail="selectAttendance" />
        </div>
      </UiParentCard>
    </v-col>
  </v-row>
</template>
<style lang="scss">
.verifybtn {
  height: 12px !important;
  width: 12px !important;
  margin-top: -3px;
}

.action-position {
  .vue3-easy-data-table__main {
    .vue3-easy-data-table__header {
      th {
        &:last-child {
          padding-left: 35px;
        }
      }
    }
  }
}

@media (max-width: 475px) {
  .easy-data-table__rows-selector {
    width: 50px !important;
    margin: 0 15px !important;
  }

  .vue3-easy-data-table__footer {
    text-align: center;
    padding-top: 15px !important;
  }
}
</style>
