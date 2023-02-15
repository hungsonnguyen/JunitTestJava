package com.zeusbe.service.impl;

import com.zeusbe.dto.ItemSwapDto;
import com.zeusbe.dto.MySwapDTO;
import com.zeusbe.dto.ProductSwapDTO;
import com.zeusbe.dto.request.ConfirmSwapReq;
import com.zeusbe.dto.request.RequestSwapReq;
import com.zeusbe.exception.RecordNotFoundException;
import com.zeusbe.model.Exchange;
import com.zeusbe.model.Product;
import com.zeusbe.model.Profile;
import com.zeusbe.model.User;
import com.zeusbe.model.enumeration.ConfirmStatus;
import com.zeusbe.model.enumeration.ExchangeAction;
import com.zeusbe.model.enumeration.ExchangeStatus;
import com.zeusbe.model.enumeration.ExchangeType;
import com.zeusbe.repository.ExchangeRepository;
import com.zeusbe.repository.ProductRepository;
import com.zeusbe.repository.ProfileRepository;
import com.zeusbe.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.zeusbe.service.ExchangeService;

@Service
public class ExchangeServiceImpl implements ExchangeService {
    @Autowired
    ExchangeRepository exchangeRepository;
    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserService userService;

    @Autowired
    ProfileRepository profileRepository;

    Logger logger = LoggerFactory.getLogger(ExchangeServiceImpl.class);

    @Override
    public Boolean requestSwap(RequestSwapReq requestSwapReq) {
        Optional<Product> sendProductOptional = productRepository.findById(requestSwapReq.getSendProductId());
        Optional<Product> receiveProductOptional = productRepository.findById(requestSwapReq.getReceiveProductId());

        if (!sendProductOptional.isPresent()) {
            throw new RecordNotFoundException("Product not found with id:" + requestSwapReq.getSendProductId());
        }
        if (!receiveProductOptional.isPresent()) {
            throw new RecordNotFoundException("Product not found with id:" + requestSwapReq.getReceiveProductId());
        }

        Product sendProduct = sendProductOptional.get();
        Product receiveProduct = receiveProductOptional.get();

        boolean hasExchangeBySendAndReceive = exchangeRepository.existsBySendProductAndReceiveProduct(sendProduct, receiveProduct);
        boolean hasExchangeByReceiveAndSend = exchangeRepository.existsByReceiveProductAndSendProduct(receiveProduct, sendProduct);

        if (hasExchangeByReceiveAndSend || hasExchangeBySendAndReceive) {
            throw new RecordNotFoundException("This exchange already exist.");

        }

//         Get current user
        User currentUser = userService.getCurrentUser().get();
        Profile currentProfile = profileRepository.findOneByUser(currentUser).get();


//        final int MAXIMUM_COUNT_REQUEST = currentProfile.getLevel().getLimitSwap() != null ? currentProfile.getLevel().getLimitSwap() : 0;

        if (sendProduct.getProfile().getId() != currentProfile.getId()) {
            throw new RecordNotFoundException("Product id :" + sendProduct.getProfile().getId() + "not belongs to this user");
        }
        if (sendProduct.getProfile().getId() == receiveProduct.getProfile().getId()) {
            throw new RecordNotFoundException("Two products belongs to the same owner");
        }

//        Check limit when the send request by level
//        if (sendProduct.getRequestCount() >= MAXIMUM_COUNT_REQUEST) {
//            throw new RecordNotFoundException("Product id: " + sendProduct.getId() + "beyond maximum swap list");
//        }

        Exchange exchangeSave = Exchange
                .builder()
                .ownerId(sendProduct.getProfile().getId())
                .ownerConfirm(ConfirmStatus.ACCEPT)
                .status(ExchangeStatus.WAITING)
                .sendProduct(sendProduct)
                .receiveProduct(receiveProduct)
                .active(true)
                .exchangerConfirm(ConfirmStatus.WAITING)
                .exchangerId(receiveProduct.getProfile().getId())
                .chatting(false)
                .createdDate(ZonedDateTime.now())
                .createdBy(sendProduct.getProfile().getId())
                .updatedDate(ZonedDateTime.now())
                .updatedBy(sendProduct.getProfile().getId())
                .build();

//        Update requestCount of sendProduct
        Integer currentRequestCount = sendProduct.getRequestCount() + 1;
        sendProduct.setReceiveCount(currentRequestCount);


//        if (currentRequestCount >= MAXIMUM_COUNT_REQUEST) {
//            sendProduct.setIsSwapAvailable(false);
//        }


//        Update receiveCount of receiveProduct
        receiveProduct.setReceiveCount(receiveProduct.getReceiveCount() + 1);

        exchangeRepository.save(exchangeSave);
        productRepository.save(sendProduct);
        productRepository.save(receiveProduct);

        return true;
    }

    @Override
    public boolean confirmSwap(ConfirmSwapReq confirmSwapReq) {
        logger.debug("Request service to check and update exchange {}", confirmSwapReq);
        Exchange currentExchange = validationExchange(confirmSwapReq.getExchangeId());


        Product sentProduct = currentExchange.getSendProduct();
        Product receiveProduct = currentExchange.getReceiveProduct();

//        Get current user
        User user = userService.getCurrentUser().get();
        Profile currentProfile = profileRepository.findOneByUser(user).get();
        final int MAXIMUM_COUNT_REQUEST = currentProfile.getLevel().getLimitSwap() != null ? currentProfile.getLevel().getLimitSwap() : 0;

        logger.debug("Get current profile with id : " + currentProfile);

//        flag check delete exchange
        boolean hasDelete = false;
        boolean isCancel = false;

//        check current user is send or receive
        if (currentProfile.getId().equals(currentExchange.getOwnerId())) {
//            This user is sender
            logger.debug("Current user is requester!");
            switch (confirmSwapReq.getAction()) {
                case CANCEL:
                    if (
                            currentExchange.getExchangerConfirm() == ConfirmStatus.CANCEL ||
                                    currentExchange.getExchangerConfirm() == ConfirmStatus.WAITING
                    ) {
//                        Remove exchange with set flag delete
                        hasDelete = true;

                    } else {
//                        handle trick
                        if (currentExchange.getOwnerConfirm() == ConfirmStatus.CANCEL) {
                            return true;
                        }
                        currentExchange.setOwnerConfirm(ConfirmStatus.CANCEL);
                    }
//                    when user cancel RequestCount, decrease requestCount = 1;
                    sentProduct = decreaseCount(sentProduct, true, MAXIMUM_COUNT_REQUEST);

//                    request is Cancel, set flag isCancel = true
                    isCancel = true;
                    break;
                case ACCEPT:
                    if (currentExchange.getOwnerConfirm() == ConfirmStatus.ACCEPT) {
                        return true;
                    }
                    currentExchange.setOwnerConfirm(ConfirmStatus.ACCEPT);

//                    when user Accept RequestCount increase 1
                    sentProduct = increaseCount(sentProduct, true, MAXIMUM_COUNT_REQUEST);
                    break;
            }
//            save product when increaseCount and decreaseCount
            sentProduct = productRepository.save(sentProduct);
            logger.debug("Update send product successfully {},", sentProduct);
        } else if (currentProfile.getId().equals(currentExchange.getExchangerId())) {
//            current user is receiver
            logger.debug("Current user is receiver");
            switch (confirmSwapReq.getAction()) {
                case CANCEL:
                    if (currentExchange.getOwnerConfirm() == ConfirmStatus.CANCEL) {
//                        remove this exchange by setting flag delete
                        hasDelete = true;
                    } else {
//                        handle trick
                        if (currentExchange.getExchangerConfirm() == ConfirmStatus.WAITING) {
                            return true;
                        }
                        currentExchange.setExchangerConfirm(ConfirmStatus.CANCEL);
                    }
//                    decrease receiveCount
                    receiveProduct = decreaseCount(receiveProduct, false, MAXIMUM_COUNT_REQUEST);
//                    set flag isCancel = true;
                    isCancel = true;
                    break;
                case ACCEPT:
//                    trick
                    if (currentExchange.getExchangerConfirm() == ConfirmStatus.ACCEPT) return true;
                    currentExchange.setExchangerConfirm(ConfirmStatus.ACCEPT);
                    break;
            }
//            save receiveProduct when increaseCount and decreaseCount
            receiveProduct = productRepository.save(receiveProduct);
            logger.debug("Update receive product successfully {}", receiveProduct);
        }
        if (hasDelete) {
            exchangeRepository.delete(currentExchange);
            logger.debug("Delete exchange successfully");
        } else {
            currentExchange.setUpdatedDate(ZonedDateTime.now());
            currentExchange.setUpdatedBy(currentProfile.getId());
            if (isCancel) {
                currentExchange.setStatus(ExchangeStatus.WAITING);
            }
            exchangeRepository.save(currentExchange);
            logger.debug("Update exchange successfully{}", currentExchange);
        }
        return false;
    }

    @Override
    public List<MySwapDTO> getMyExchanges(ExchangeStatus exchangeStatus, ExchangeType type, Pageable pageable) {
//        Get current profile
        User currentUser = userService.getCurrentUser().get();
        Profile currentProfile = profileRepository.findOneByUser(currentUser).get();

//        Get my list products
        List<Product> myProductList = productRepository.findByProfile(currentProfile);

        List<MySwapDTO> mySwapDTOList = new ArrayList<MySwapDTO>();
        List<Exchange> myExchangeList = new ArrayList<Exchange>();

        if (myProductList.isEmpty()) {
            return mySwapDTOList;
        }

//        check filter conditions
        if (exchangeStatus == null && type == null) {
            for (Product product : myProductList) {
//                find myExchangeList by sentProduct or receiveProduct
                myExchangeList = exchangeRepository.findBySendProductOrReceiveProduct(product, product);
//                add first prior myExchangeList
                mySwapDTOList = appendToSwpapList(mySwapDTOList, myExchangeList, product);
            }
        } else if (exchangeStatus != null && type == null) {
            for (Product product : myProductList) {
//                find myExchangeList by sendProduct or receiveProduct and filter by status
                myExchangeList = exchangeRepository.findBySendProductAndStatusOrReceiveProductAndStatus(
                        product,
                        exchangeStatus,
                        product,
                        exchangeStatus

                );
                mySwapDTOList = appendToSwpapList(mySwapDTOList, myExchangeList, product);
            }
        } else if (exchangeStatus == null && type != null) {
            switch (type) {
                case SENT:
                    for (Product product : myProductList) {
                        myExchangeList = exchangeRepository.findBySendProduct(product);
                        mySwapDTOList = appendToSwpapList(mySwapDTOList, myExchangeList, product);
                    }
                    break;
                case RECEIVED:
                    for (Product product : myProductList) {
                        myExchangeList = exchangeRepository.findByReceiveProduct(product);
                        mySwapDTOList = appendToSwpapList(mySwapDTOList, myExchangeList, product);
                    }
                    break;
                default:
                    break;
            }
        } else if (exchangeStatus != null && type != null) {
            switch (type) {
                case SENT:
                    for (Product product : myProductList) {
                        myExchangeList = exchangeRepository.findBySendProductAndStatus(product, exchangeStatus);
                        mySwapDTOList = appendToSwpapList(mySwapDTOList, myExchangeList, product);
                    }
                    break;
                case RECEIVED:
                    for (Product product : myProductList) {
                        myExchangeList = exchangeRepository.findByReceiveProductAndStatus(product, exchangeStatus);
                        mySwapDTOList = appendToSwpapList(mySwapDTOList, myExchangeList, product);
                    }
                    break;
                default:
                    break;

            }

        }
        if (myExchangeList.isEmpty()) {
            throw new RecordNotFoundException("my exchange is not existing!");
        }

        return mySwapDTOList;
    }

    @Override
    public List<ItemSwapDto> getExchangesByProductId(Long productId, ExchangeType type, Pageable pageable) {
////        create new pageable to combie sort by update date
//        Pageable newPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("updateDate").descending());

        User currentUser = userService.getCurrentUser().get();
        Profile currentProfile = profileRepository.findOneByUser(currentUser).get();

//        validate product
        Optional<Product> productOptional = productRepository.findById(productId);
        if (!productOptional.isPresent()) {
            throw new RecordNotFoundException("Product with id :" + productId + " not existing exchange!");
        }

        Product myProduct = productOptional.get();
        if (myProduct.getProfile().getId() != currentProfile.getId()) {
            throw new RecordNotFoundException("Product with id:" + myProduct.getId() + "not belong with user!");
        }

        List<ItemSwapDto> itemSwapDtoList = new ArrayList<>();
        List<Exchange> exchangeList = null;

        /**
         * get list of exchanges by productID and exchange type. If type of exchange is SENT then get exchanges by sendProdcut.
         * If type of exchange is RECEIVE then get exchanges by receiveProduct
         * If type of exchange is NULL then get ex exchanges by sendProduct or receiveProduct         *
         */
        if (type != null) {
            switch (type) {
                case SENT:
                    exchangeList = exchangeRepository.findBySendProduct(myProduct);
                    break;
                case RECEIVED:
                    exchangeList = exchangeRepository.findByReceiveProduct(myProduct);
                    break;

            }
        }
        exchangeList = exchangeRepository.findBySendProductOrReceiveProduct(myProduct, myProduct);

        if (exchangeList.isEmpty()) {
            throw new RecordNotFoundException("Not found exchange be long product with id : " + productId);
        }
        ProductSwapDTO myProductSwapDTO = ProductSwapDTO
                .builder()
                .productId(myProduct.getId())
                .productName(myProduct.getProductName())
                .thumbnail(myProduct.getThumbnail())
                .build();
        for (Exchange exchange : exchangeList) {
            Product exchangeProduct = null;
            ExchangeType swapType = null;
            ConfirmStatus ownerConfirm = null;
            ConfirmStatus exchangerConfirm = null;
            Profile exchangerProfile = null;

//            define exchangeProduct, ownerConfirm, exchangerConfirm
            if (exchange.getSendProduct().getId() == productId) {
                exchangeProduct = exchange.getReceiveProduct();
                ownerConfirm = exchange.getOwnerConfirm();
                exchangerConfirm = exchange.getExchangerConfirm();
                swapType = ExchangeType.SENT;
            } else {
                exchangeProduct = exchange.getReceiveProduct();
                ownerConfirm = exchange.getExchangerConfirm();
                exchangerConfirm = exchange.getOwnerConfirm();
                swapType = ExchangeType.RECEIVED;
            }
            exchangerProfile = exchangeProduct.getProfile();
            ProductSwapDTO exchangerProductDto = ProductSwapDTO
                    .builder()
                    .productId(exchangeProduct.getId())
                    .productName(exchangeProduct.getProductName())
                    .thumbnail(exchangeProduct.getThumbnail())
                    .username(exchangeProduct.getProfile().getUser().getLogin())
                    .uid(exchangerProfile.getUid())
                    .phone(exchangerProfile.getPhone())
                    .reciverProfileId(exchangerProfile.getId())
                    .avatarUrl(exchangerProfile.getAvatar())
                    .build();

            ItemSwapDto itemSwapDto = ItemSwapDto
                    .builder()
                    .exchangeId(exchange.getId())
                    .ownerConfirm(ownerConfirm)
                    .exchangerConfirm(exchangerConfirm)
                    .exchangeStatus(exchange.getStatus())
                    .swapType(swapType)
                    .myProduct(myProductSwapDTO)
                    .exchangeProduct(exchangerProductDto)
                    .chatting(exchange.getChatting())
                    .build();
            itemSwapDtoList.add(itemSwapDto);
        }
        if (itemSwapDtoList.isEmpty()) {
            throw new RecordNotFoundException("Product is not existing exchange!");
        }
        return itemSwapDtoList;
    }

    @Override
    public void startChatting(Long exchangerId) {
        User currentUser = userService.getCurrentUser().get();
        Profile currentProfile = profileRepository.findOneByUser(currentUser).get();


    }

    private List<MySwapDTO> appendToSwpapList(List<MySwapDTO> mySwapDTOList, List<Exchange> myExchangeList, Product product) {
        if (!myExchangeList.isEmpty()) {
            for (Exchange exchange : myExchangeList) {
                MySwapDTO mySwapDTO = this.convertExchangeToProduct(exchange, product);
                if (mySwapDTO != null) {
                    mySwapDTOList.add(mySwapDTO);
                }
            }


        }
        return mySwapDTOList;
    }

    private MySwapDTO convertExchangeToProduct(Exchange exchange, Product product) {
        MySwapDTO mySwapDTO = null;

        if (exchange.getOwnerConfirm() == ConfirmStatus.CANCEL && exchange.getExchangerConfirm() == ConfirmStatus.CANCEL) {
            // ignore exchange was canceled
            return mySwapDTO;
        }
        if (exchange.getStatus() == ExchangeStatus.SWAPPING) {
            mySwapDTO = new MySwapDTO();
            mySwapDTO.setExchangeId(exchange.getId());
            mySwapDTO.setProductId(product.getId());
            mySwapDTO.setAvatarProduct(product.getThumbnail());
            mySwapDTO.setProductName(product.getProductName());
            mySwapDTO.setExchangeStatus(exchange.getStatus());
            mySwapDTO.setUpdatedDate(product.getUpdatedDate());

        }
        if (mySwapDTO == null) {
            mySwapDTO = new MySwapDTO();
            mySwapDTO.setExchangeId(exchange.getId());
            mySwapDTO.setProductId(product.getId());
            mySwapDTO.setAvatarProduct(product.getThumbnail());
            mySwapDTO.setProductName(product.getProductName());
            mySwapDTO.setExchangeStatus(exchange.getStatus());
            mySwapDTO.setUpdatedDate(product.getUpdatedDate());
        }

        return mySwapDTO;

    }

    private Product increaseCount(Product product, boolean isRequestCount, Integer MAXIMUM_COUNT_REQUEST) {
        if (isRequestCount) {
            Integer currentRequestCount = product.getRequestCount() + 1;
            product.setRequestCount(currentRequestCount);
            if (currentRequestCount >= MAXIMUM_COUNT_REQUEST) {
                product.setIsSwapAvailable(false);
            }
        } else {
            product.setReceiveCount(product.getReceiveCount() + 1);
        }
        return product;
    }


    private Product decreaseCount(Product product, boolean isRequestCount, Integer MAXIMUM_REQUEST_COUNT) {
        if (isRequestCount) {
            Integer currentRequestCount = product.getRequestCount() - 1;
            product.setRequestCount(currentRequestCount);
            if (currentRequestCount < MAXIMUM_REQUEST_COUNT) {
                product.setIsSwapAvailable(true);
            }
        } else {
            product.setReceiveCount(product.getRequestCount() - 1);
            if (product.getReceiveCount() < 0) {
                product.setReceiveCount(0);
            }
        }
        return product;
    }

    private Exchange validationExchange(Long exchangeId) {
        logger.debug("Validate exchange with id:" + exchangeId);

        if (!exchangeRepository.findById(exchangeId).isPresent()) {
            throw new RecordNotFoundException("Not found exchange with id: " + exchangeId);
        }
        return exchangeRepository.findById(exchangeId).get();
    }
}
